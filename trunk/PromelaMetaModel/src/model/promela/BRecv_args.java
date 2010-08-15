package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

/**
 * "Bracketed" receive argument
 * 
 * @author Duc
 * 
 */
public class BRecv_args extends IRecv_args {
	private List<Recv_arg> args = new ArrayList<Recv_arg>();

	public BRecv_args(Recv_arg ra) {
		super();
		addRecvArg(ra);
	}

	public BRecv_args(List<Recv_arg> args) {
		super();
		setArgs(args);
	}

	/**
	 * Create a "receive arguments" of 2 arguments.
	 * 
	 * @param arg1
	 * @param arg2
	 */
	public BRecv_args(String arg1, String arg2) {
		addRecvArg(new Recv_arg(arg1));
		addRecvArg(new Recv_arg(arg2));
	}

	/**
	 * Create an empty receive-argument.
	 */
	public BRecv_args() {
		super();
	}

	public void addRecvArg(Recv_arg ra) {
		args.add(ra);
	}

	/**
	 * Add one receive argument to this receive-arguments
	 * 
	 * @param arg
	 */
	public void addRecvArg(String arg) {
		args.add(new Recv_arg(arg));
	}

	public List<Recv_arg> getArgs() {
		return args;
	}

	public void setArgs(List<Recv_arg> args) {
		this.args = args;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (args.size() == 0)
			throw new RuleViolationException("there is at least one recv_arg");
		String ret = args.get(0).toCode();
		int size = args.size();
		for (int i = 1; i < size; i++)
			ret = ret + "," + args.get(i).toCode();
		return ret;
	}
}
