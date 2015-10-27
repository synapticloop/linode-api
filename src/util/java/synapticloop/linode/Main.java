package synapticloop.linode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import synapticloop.linode.bean.Api;
import synapticloop.linode.bean.ApiMethod;
import synapticloop.linode.bean.ApiMethodParam;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class Main {
	private static final int NODE_SMALL = 2;
	private static final int NODE_STRONG = 1;
	private static final int NODE_TEXT = 0;
	private static Map<String, Integer> NODE_NAME_MAP = new HashMap<String, Integer>();
	static {
		NODE_NAME_MAP.put("#text", NODE_TEXT);
		NODE_NAME_MAP.put("strong", NODE_STRONG);
		NODE_NAME_MAP.put("small", NODE_SMALL);
	}

	private static Map<String, Api> apiBeanCache = new HashMap<String, Api>();

	public static void main(String[] args) throws ParseException, RenderException, IOException {
		// here we are going to walk the directory and 
		File apiDocsDirectory = new File("./api-docs");
		listFiles(apiDocsDirectory.listFiles());

		new File("./src/main/java/synapticloop/linode/api/").mkdirs();

		// now that we have all of the apis
		Iterator<String> iterator = apiBeanCache.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Api api = apiBeanCache.get(key);
			TemplarContext templarContext = new TemplarContext();
			templarContext.add("api", api);
			Parser parser = new Parser(Main.class.getResourceAsStream("/java-create-api.templar"));
			String pathname = "./src/main/java/synapticloop/linode/api/" + api.getClassName() + ".java";
			System.out.println("Generating for: " + api.getClassName());
			File outFile = new File(pathname);
			FileWriter fileWriter = new FileWriter(outFile);
			fileWriter.write(parser.render(templarContext));
			fileWriter.close();
		}

	}

	private static void listFiles(File[] files) {
		for (File file : files) {
			if(file.isDirectory()) {
				listFiles(file.listFiles());
			} else {
				String absolutePath = file.getAbsolutePath();
				if(!absolutePath.endsWith("index.html") && !absolutePath.endsWith("robots.txt")) {
					parseFile(file);
				}
			}
		}

	}

	private static void parseFile(File file) {
		try {
			Document document = Jsoup.parse(file, "UTF-8");
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
						System.out.println("unknown node name of '" + nodeName + "'.");
					} else {
						switch (NODE_NAME_MAP.get(nodeName)) {
						case NODE_TEXT:
							if(node.toString().contains("-")) {
								apiMethodParam.addParameterInformation(node.toString());
							}
							break;
						case NODE_SMALL:
							apiMethodParam.addDescription(node.childNode(0).toString());
							break;
						case NODE_STRONG:
							String nodeString = node.childNode(0).toString();
							apiMethodParam.addParameterName(nodeString);
							api.addConstant(nodeString);
							break;
						default:
							System.out.println("uncaught switch statement for node '" + nodeName + "'.");
							break;
						}
					}
				}

				apiMethod.addApiMethodParam(apiMethodParam);
			}

			apiMethod.addExampleResponse(document.select("pre.highlight").first().text());

			Elements apiErrors = document.select("code.api-error");
			Iterator<Element> apiErrorIterator = apiErrors.iterator();

			while (apiErrorIterator.hasNext()) {
				Element apiError = (Element) apiErrorIterator.next();
				apiMethod.addApiError(apiError.text());
			}

			api.addApiMethod(apiMethod);


		} catch (IOException ex) {
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
			Api api = new Api(apiName);
			apiBeanCache.put(apiName, api);
			return(api);
		}
	}

}
