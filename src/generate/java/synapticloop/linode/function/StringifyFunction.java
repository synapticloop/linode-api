package synapticloop.linode.function;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.function.Function;
import synapticloop.templar.utils.TemplarContext;

public class StringifyFunction extends Function {

	public StringifyFunction() {
	}

	public StringifyFunction(int numArgs) {
		super(1);
	}

	public StringifyFunction(int numArgs, int numArgsMax) {
		super(1, 1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		return(args[0].toString().replace("\"", "\\\""));
	}
}
