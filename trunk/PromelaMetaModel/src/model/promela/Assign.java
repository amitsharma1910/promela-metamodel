package model.promela;

import exceptions.RuleViolationException;

public class Assign extends IStmnt {
	private IAssign iassign;

	public Assign(IAssign iassign) {
		super();
		setIassign(iassign);
	}

	/**
	 * Create an increment or decrement assign statement increment==true -->
	 * make an increment assign statement increment==false --> make an decrement
	 * assign statement
	 * 
	 * @param varName
	 * @param increment
	 */
	public Assign(String varName, boolean increment) {
		if (increment)
			iassign = new IncAssign(varName);
		else
			iassign = new DecAssign(varName);
	}

	/**
	 * Create an increment or decrement assign statement increment==true -->
	 * make an increment assign statement increment==false --> make an decrement
	 * assign statement
	 * 
	 * @param varName
	 * @param increment
	 */
	public Assign(String varName, String fieldName, boolean increment) {
		if (increment)
			iassign = new IncAssign(varName, fieldName);
		else
			iassign = new DecAssign(varName, fieldName);
	}

	/**
	 * Create a "normal" assignment with constant
	 * 
	 * @param varName
	 *            variable name
	 * @param cnst
	 *            assigned constant value
	 */
	public Assign(String varName, Const cnst) {
		setIassign(new SAssign(varName, cnst));
	}

	/**
	 * Create a "normal" assignment with constant
	 * 
	 * @param varName
	 *            variable name
	 * @param varref
	 *            assigned mtype value or a varref
	 */
	public Assign(String varName, String varref) {
		setIassign(new SAssign(varName, varref));
	}

	/**
	 * Create a "normal" assignment with field name and constant
	 * 
	 * @param varName
	 *            variable name
	 * @param fieldName
	 *            the field of the variable
	 * @param cnst
	 *            assigned constant value
	 */
	public Assign(String varName, String fieldName, Const cnst) {
		setIassign(new SAssign(varName, fieldName, cnst));
	}

	/**
	 * Create a "normal" assignment with field name and varref
	 * 
	 * @param varName
	 *            variable name
	 * @param fieldName
	 *            the field of the variable
	 * @param varref
	 *            assigned string value or variable reference
	 */
	public Assign(String varName, String fieldName, String varref) {
		setIassign(new SAssign(varName, fieldName, varref));
	}

	/**
	 * Create a "normal" assignment: var1.field1=var2.field2
	 * 
	 * @param varName1
	 * @param fieldName1
	 * @param varName2
	 * @param fieldName2
	 */
	public Assign(String varName1, String fieldName1, String varName2, String fieldName2) {
		setIassign(new SAssign(varName1, fieldName1, varName2, fieldName2));
	}

	public IAssign getIassign() {
		return iassign;
	}

	public void setIassign(IAssign iassign) {
		this.iassign = iassign;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (iassign == null)
			throw new RuleViolationException("the assign must not be emtpy");
		return getIassign().toCode();
	}
}
