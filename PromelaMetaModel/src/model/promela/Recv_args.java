package model.promela;

import exceptions.RuleViolationException;

public class Recv_args implements ToCode {
	private IRecv_args recv_args;

	public Recv_args(IRecv_args recvArgs) {
		super();
		setRecv_args(recvArgs);
	}

	/**
	 * Create a receive argument with 2 variables
	 * 
	 * @param arg1
	 * @param arg2
	 */
	public Recv_args(String arg1, String arg2) {
		super();
		recv_args = new BRecv_args(arg1, arg2);
	}

	/**
	 * Create empty receive-arguments; normal arguments are created by default
	 */
	public Recv_args() {
		super();
		recv_args = new BRecv_args();
	}

	/**
	 * Add one argument to receive arguments
	 * 
	 * @param arg
	 */
	public void addRecv_arg(String arg) {
		recv_args.addRecvArg(arg);
	}

	public IRecv_args getRecv_args() {
		return recv_args;
	}

	public void setRecv_args(IRecv_args recvArgs) {
		recv_args = recvArgs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (recv_args == null)
			throw new RuleViolationException("recv_args must not be empty");
		return recv_args.toCode();
	}
}
