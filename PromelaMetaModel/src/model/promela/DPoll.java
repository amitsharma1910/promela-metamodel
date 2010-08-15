package model.promela;

import exceptions.RuleViolationException;

public class DPoll extends IPoll {
	private Varref varref;
	private Recv_args args;

	public DPoll(Varref varref, Recv_args args) {
		super();
		setArgs(args);
		setVarref(varref);
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public Recv_args getArgs() {
		return args;
	}

	public void setArgs(Recv_args args) {
		this.args = args;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null || args == null)
			throw new RuleViolationException("This poll must contain both varref and recv_arg");
		return getVarref().toCode() + "??[" + getArgs().toCode() + "]";
	}
}
