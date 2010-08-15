package model.promela;

import exceptions.RuleViolationException;

public class ChanVar extends IExpr {
	private Chanpoll chanpoll;
	private Varref varref;

	public ChanVar(Chanpoll chanpoll, Varref varref) {
		super();
		setChanpoll(chanpoll);
		setVarref(varref);
	}

	public Chanpoll getChanpoll() {
		return chanpoll;
	}

	public void setChanpoll(Chanpoll chanpoll) {
		this.chanpoll = chanpoll;
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (chanpoll == null || varref == null)
			throw new RuleViolationException("chanpoll and varref must not be empty");
		return getChanpoll() + " (" + getVarref().toCode() + ")";
	}
}
