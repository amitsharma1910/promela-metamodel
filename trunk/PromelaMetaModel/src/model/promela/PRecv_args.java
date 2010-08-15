package model.promela;

import exceptions.RuleViolationException;

/**
 * Receive argument with parentheses
 * 
 * @author Duc
 * 
 */
public class PRecv_args extends IRecv_args {
	private Recv_arg recv_arg;
	private Recv_args recv_args;

	public PRecv_args(Recv_arg recvArg1, Recv_args recvArgs) {
		super();
		setRecv_arg(recvArg1);
		setRecv_args(recvArgs);
	}

	public void addRecvArg(String arg) {
		if (recv_arg == null)
			recv_arg = new Recv_arg(arg);
		else {
			Recv_args r = new Recv_args();
			r.addRecv_arg(arg);
			this.recv_args = r;
		}
	}

	public Recv_arg getRecv_arg() {
		return recv_arg;
	}

	public void setRecv_arg(Recv_arg recvArg) {
		recv_arg = recvArg;
	}

	public Recv_args getRecv_args() {
		return recv_args;
	}

	public void setRecv_args(Recv_args recvArg2) {
		recv_args = recvArg2;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (recv_arg == null || recv_args == null)
			throw new RuleViolationException("Receive arguments must not be empty");
		return getRecv_arg().toCode() + " (" + getRecv_args().toCode() + ")";
	}
}
