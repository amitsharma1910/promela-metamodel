package model.promela;

import exceptions.RuleViolationException;

/**
 * Standard assign statement.
 * 
 * @author Duc
 * 
 */
public class SAssign extends IAssign {
	private Varref varref;
	private Any_expr expr;

	public SAssign(Varref varref, Any_expr expr) {
		super();
		setVarref(varref);
		setExpr(expr);
	}

	/**
	 * Create a "normal" assignment with constant
	 * 
	 * @param varName
	 *            variable name
	 * @param varref
	 *            assigned variable
	 */
	public SAssign(String varName, Varref varref) {
		setVarref(varName);
		setExpr(new Any_expr(varref));
	}

	/**
	 * Create a "normal" assignment with constant
	 * 
	 * @param varName
	 *            variable name
	 * @param cnst
	 *            assigned constant value
	 */
	public SAssign(String varName, Const cnst) {
		setVarref(varName);
		setExpr(new Any_expr(cnst));
	}

	/**
	 * Create a "normal" assignment with constant
	 * 
	 * @param varName
	 *            variable name
	 * @param varref
	 *            assigned varref or mtype value
	 */
	public SAssign(String varName, String varref) {
		setVarref(varName);
		setExpr(new Any_expr(new Varref(varref)));
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
	public SAssign(String varName, String fieldName, Const cnst) {
		setVarref(new Varref(varName, fieldName));
		setExpr(new Any_expr(cnst));
	}

	/**
	 * Create a "normal" assignment: var1.field1=var2.field2
	 * 
	 * @param varName1
	 * @param fieldName1
	 * @param varName2
	 * @param fieldName2
	 */
	public SAssign(String varName1, String fieldName1, String varName2, String fieldName2) {
		setVarref(new Varref(varName1, fieldName1));
		setExpr(new Any_expr(new Varref(varName2, fieldName2)));
	}

	/**
	 * Create a "normal" assignment with field name and varref
	 * 
	 * @param varName
	 *            variable name
	 * @param fieldName
	 *            the field of the variable
	 * @param cnst
	 *            assigned string value or variable reference
	 */
	public SAssign(String varName, String fieldName, String varref) {
		setVarref(new Varref(varName, fieldName));
		setExpr(new Any_expr(new Varref(varref)));
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

	public Any_expr getExpr() {
		return expr;
	}

	public void setExpr(Any_expr expr) {
		this.expr = expr;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null || expr == null)
			throw new RuleViolationException("varref and any_expr must not be empty");
		return getVarref().toCode() + "=" + getExpr().toCode();
	}
}
