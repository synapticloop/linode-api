package synapticloop.linode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import synapticloop.linode.api.response.bean.Datacenter;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Kernel;
import synapticloop.linode.api.response.bean.LinodePlan;
import synapticloop.linode.exception.ApiException;
import synapticloop.linode.function.SlugFunction;
import synapticloop.linode.logger.SimpleLogger;
import synapticloop.templar.Parser;
import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class SlugMain {
	private static final String JAVA_SRC_LINODE_API_OUTPUT_DIRECTORY = "./src/main/java/synapticloop/linode/";

	public static void main(String[] args) throws ApiException, ParseException, IOException, RenderException {
		LinodeApi linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
		List<Datacenter> datacenters = linodeApi.getAvailDatacenters().getDatacenters();
		List<Distribution> distributions = linodeApi.getAvailDistributions().getDistributions();
		List<Kernel> kernels = linodeApi.getAvailKernels().getKernels();
		List<LinodePlan> linodePlans = linodeApi.getAvailLinodePlans().getLinodePlans();

		TemplarContext templarContext = new TemplarContext();
		templarContext.add("datacenters", datacenters);
		templarContext.add("distributions", distributions);
		templarContext.add("kernels", kernels);
		templarContext.add("linodePlans", linodePlans);
		try {
			templarContext.addFunction("slug", new SlugFunction(1));
		} catch (FunctionException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		parseAndCreate(templarContext, "/java-create-datacenter-slug.templar", "DatacenterSlug.java");
		parseAndCreate(templarContext, "/java-create-distribution-slug.templar", "DistributionSlug.java");
		parseAndCreate(templarContext, "/java-create-kernel-slug.templar", "KernelSlug.java");
		parseAndCreate(templarContext, "/java-create-linodeplan-slug.templar", "PlanSlug.java");
	}

	private static void parseAndCreate(TemplarContext templarContext, String resource, String pathName) throws ParseException, IOException, RenderException {
		Parser parser = new Parser(Main.class.getResourceAsStream(resource));
		String pathname = JAVA_SRC_LINODE_API_OUTPUT_DIRECTORY + pathName;
		SimpleLogger.log("Generating for: " + pathName);
		File outFile = new File(pathname);
		FileWriter fileWriter = new FileWriter(outFile);
		fileWriter.write(parser.render(templarContext));
		fileWriter.close();
	}
}
