package model.promela;

import exceptions.RuleViolationException;

public class IncAssign extends IAssign {
	private Varref varref;

	public IncAssign(Varref varref) {
		super();
		setVarref(varref);
	}

	/**
	 * Create new increment assignment statement
	 * 
	 * @param varName
	 */
	public IncAssign(String varName) {
		super();
		setVarref(varName);
	}

	/**
	 * Create new increment assignment statement
	 * 
	 * @param varName
	 */
	public IncAssign(String varName, String fieldName) {
		super();
		setVarref(new Varref(varName, fieldName));
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public void setVarref(String varref) {
		this.varref = new Varref(varref);
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null)
			throw new RuleViolationException("varref must not be empty");
		return getVarref().toCode() + "++";
	}
}
