package synapticloop.linode.function;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.TemplarContext;

public class SlugFunction extends Function {

	public SlugFunction() {
	}

	public SlugFunction(int numArgs) {
		super(1);
	}

	public SlugFunction(int numArgs, int numArgsMax) {
		super(1, 1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		return(args[0].toString().replaceAll("[^a-zA-z0-9]", "_").toUpperCase().replaceAll("__", "_"));
	}
}
