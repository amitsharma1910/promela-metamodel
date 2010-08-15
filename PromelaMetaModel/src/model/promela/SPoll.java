package model.promela;

import exceptions.RuleViolationException;

/**
 * Poll without side-effect
 * 
 * @author Duc
 * 
 */
public class SPoll extends IPoll {
	private Varref varref;
	private Recv_args recvs;

	public SPoll(Varref varref, Recv_args recvs) {
		super();
		setRecvs(recvs);
		setVarref(varref);
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public Recv_args getRecvs() {
		return recvs;
	}

	public void setRecvs(Recv_args recvs) {
		this.recvs = recvs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null || recvs == null)
			throw new RuleViolationException("This poll must contain both varref and recv_arg");
		return getVarref().toCode() + "?[" + getRecvs().toCode() + "]";
	}
}
