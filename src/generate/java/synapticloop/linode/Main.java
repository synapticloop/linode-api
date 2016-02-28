package synapticloop.linode;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import synapticloop.linode.bean.Api;
import synapticloop.linode.bean.ApiMethod;
import synapticloop.linode.bean.ApiMethodParam;
import synapticloop.linode.bean.ErrorCodeMapper;
import synapticloop.linode.logger.SimpleLogger;

public class Main {
	private static final String API_DOCS_DIRECTORY = "./api-docs";
	private static final String JAVA_SRC_API_OUTPUT_DIRECTORY = "./src/main/java/synapticloop/linode/api/request/";
	private static final String JAVA_SRC_LINODE_API_OUTPUT_DIRECTORY = "./src/main/java/synapticloop/linode/";

	private static final int NODE_TEXT = 0;
	private static final int NODE_STRONG = 1;
	private static final int NODE_SMALL = 2;

	private static Map<String, Integer> NODE_NAME_MAP = new HashMap<String, Integer>();
	static {
		NODE_NAME_MAP.put("#text", NODE_TEXT);
		NODE_NAME_MAP.put("strong", NODE_STRONG);
		NODE_NAME_MAP.put("small", NODE_SMALL);
	}

	private static Set<String> ignoreFiles = new HashSet<String>();
	static {
		ignoreFiles.add("index.html");
		ignoreFiles.add("robots.txt");
	}

	private static FileFilter fileFilter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			String name = file.getName();
			if(ignoreFiles.contains(name) || name.startsWith(".")) {
				return(false);
			}
			return(true);
		}
	};

	private static Map<String, Api> apiBeanCache = new HashMap<String, Api>();
	private static List<Api> apis = new ArrayList<Api>();



	private static void parseFiles(File[] files) {
		for (File file : files) {
			if(file.isDirectory()) {
				parseFiles(file.listFiles(fileFilter));
			} else {
				parseFile(file);
			}
		}
	}

	private static void parseFile(File file) {
		SimpleLogger.log("Parsing file: " + file.getName());
		Document document = null;
		try {
			document = Jsoup.parse(file, "UTF-8");
			String title = document.select("h1.h2").first().text();
			if(!title.contains("()")) {
				return;
			}

			Api api = getApiForFile(file);

			String absolutePath = file.getAbsolutePath();
			String apiFileName = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);

			ApiMethod apiMethod = new ApiMethod(apiFileName);

			apiMethod.addDescription(document.select(".col-sm-8 p").first().toString());

			Elements apiParams = document.select(".api-params li");
			Iterator<Element> apiParam = apiParams.iterator();
			while (apiParam.hasNext()) {
				Element element = (Element) apiParam.next();
				List<Node> childNodes = element.childNodes();
				ApiMethodParam apiMethodParam = new ApiMethodParam();

				for (Node node : childNodes) {
					String nodeName = node.nodeName();
					if(!NODE_NAME_MAP.containsKey(nodeName)) {
						SimpleLogger.log("unknown node name of '" + nodeName + "', update Main.parseFile() switch");
					} else {
						switch (NODE_NAME_MAP.get(nodeName)) {
						case NODE_TEXT:
							if(node.toString().contains("-")) {
								apiMethodParam.addParameterInformation(node.toString());
							}
							break;
						case NODE_SMALL:
							apiMethodParam.addDescription(node.childNode(0).toString().trim());
							break;
						case NODE_STRONG:
							String nodeString = node.childNode(0).toString();
							apiMethodParam.addParameterName(nodeString);
							api.addConstant(nodeString);
							break;
						default:
							SimpleLogger.log("uncaught switch statement for node '" + nodeName + "', update Main.parseFile() switch statement");
							break;
						}
					}
				}

				apiMethod.addApiMethodParam(apiMethodParam);
			}

			String exampleResponse = document.select("pre.highlight").first().text();
			apiMethod.addExampleResponse(exampleResponse);

			Elements apiErrors = document.select("code.api-error");
			Iterator<Element> apiErrorIterator = apiErrors.iterator();

			while (apiErrorIterator.hasNext()) {
				Element apiError = (Element) apiErrorIterator.next();
				String apiErrorText = apiError.text();

				if(ErrorCodeMapper.hasErrorCode(apiErrorText)) {
					apiMethod.addApiError(apiErrorText);
					
				} else {
					SimpleLogger.log("Unknown error code '" + apiErrorText + "', update Main.parseFile() apiError checking - from file: '" + file.getPath() + "'.");
				}
			}

			api.addApiMethod(apiMethod);

		} catch (IOException ex) {
			SimpleLogger.log("IO Exception on file '" + file.getAbsolutePath() + "'.");
			ex.printStackTrace();
		}
	}

	private static Api getApiForFile(File file) {
		String absolutePath = file.getAbsolutePath();
		String apiFileName = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
		String apiName = apiFileName;
		if(apiFileName.contains(".")) {
			apiName = apiFileName.substring(0, apiFileName.indexOf("."));
		}

		if(apiBeanCache.containsKey(apiName)) {
			return(apiBeanCache.get(apiName));
		} else {
			Api api = new Api(getLocation(file.getAbsolutePath()), apiName);
			apiBeanCache.put(apiName, api);
			return(api);
		}
	}

	private static String getLocation(String fileName) {
		return("http:/" + fileName.substring(fileName.indexOf(API_DOCS_DIRECTORY) + API_DOCS_DIRECTORY.length(), fileName.lastIndexOf("/")));
	}

	
}
