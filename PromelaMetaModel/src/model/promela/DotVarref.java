package model.promela;

import exceptions.RuleViolationException;

public class DotVarref implements ToCode {
	private Varref varref;

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public DotVarref(Varref varref) {
		super();
		setVarref(varref);
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null)
			throw new RuleViolationException("varref must not be empty");
		return "." + varref.toCode();
	}
}
