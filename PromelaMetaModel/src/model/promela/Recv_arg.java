package model.promela;

import exceptions.RuleViolationException;

public class Recv_arg implements ToCode {
	private IRecv_arg recv_arg;

	/**
	 * Create a receive argument from a variable
	 * 
	 * @param var
	 */
	public Recv_arg(String var) {
		recv_arg = new RecvVarref(var);
	}

	public Recv_arg(IRecv_arg recvArg) {
		super();
		setRecv_arg(recvArg);
	}

	/**
	 * Create a receive argument from a variable reference
	 * 
	 * @param v
	 */
	public Recv_arg(Varref v) {
		RecvVarref arg = new RecvVarref(v);
		this.recv_arg = arg;
	}

	public IRecv_arg getRecv_arg() {
		return recv_arg;
	}

	public void setRecv_arg(IRecv_arg recvArg) {
		recv_arg = recvArg;
	}

	public String toCode() throws RuleViolationException {
		if (recv_arg == null)
			throw new RuleViolationException("recv_arg must not be empty");
		return recv_arg.toCode();
	}
}
