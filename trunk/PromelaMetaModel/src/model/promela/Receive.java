package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

public class Receive extends IStmnt {
	private IReceive recv;

	public Receive(IReceive recv) {
		super();
		setRecv(recv);
	}

	/**
	 * Create new NORMAL receive statement.
	 * 
	 * @param varref
	 * @param recvA
	 */
	public Receive(Varref varref, Recv_args recvA) {
		super();
		setRecv(new NReceive(varref, recvA));
	}

	/**
	 * Create a "normal" receive statement from a channel variable and var list
	 * 
	 * @param chanVar
	 * @param recvVars
	 */
	public Receive(String chanVar, List<String> recvVars) {
		setRecv(new NReceive(chanVar, recvVars));
	}

	public IReceive getRecv() {
		return recv;
	}

	public void setRecv(IReceive recv) {
		this.recv = recv;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (recv == null)
			throw new RuleViolationException("A receive statement must not be empty");
		return recv.toCode();
	}
}
