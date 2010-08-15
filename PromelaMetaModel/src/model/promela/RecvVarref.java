package model.promela;

import exceptions.RuleViolationException;

public class RecvVarref extends IRecv_arg {
	private Varref varref;

	public RecvVarref(Varref varref) {
		super();
		this.varref = varref;
	}

	public RecvVarref(String varref) {
		super();
		this.varref = new Varref(varref);
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null)
			throw new RuleViolationException("This receive argument must contain varref");
		return getVarref().toCode();
	}
}
