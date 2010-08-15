package model.promela;

import exceptions.RuleViolationException;

public class Any_expr extends IExpr {
	private IAny_expr any_expr;

	public IAny_expr getAny_expr() {
		return any_expr;
	}

	public void setAny_expr(IAny_expr anyExpr) {
		any_expr = anyExpr;
	}

	/**
	 * Create an empty any expression
	 */
	public Any_expr() {
	}

	/**
	 * Create a binary operation expression
	 * 
	 * @param any_expr1
	 * @param binarop
	 * @param any_expr2
	 */
	public Any_expr(Varref varref1, String binarop, Varref varref2) {
		setAny_expr(new BinaAny_expr(varref1, binarop, varref2));
	}

	/**
	 * Create a binary operation expression.
	 * 
	 * @param varref
	 *            Variable reference at the left-hand side
	 * @param binarop
	 * @param cnst
	 *            a constant, for right-hand side.
	 */
	public Any_expr(Varref varref, String binarop, Const cnst) {
		setAny_expr(new BinaAny_expr(varref, binarop, cnst));
	}

	/**
	 * Create a unary operation expression
	 * 
	 * @param unarop
	 * @param varref
	 */
	public Any_expr(String unarop, Varref varref) {
		setAny_expr(new UAny_expr(unarop, varref));
	}

	/**
	 * Create a unary operation expression
	 * 
	 * @param unarop
	 * @param any_expr
	 *            an any-expression
	 */
	public Any_expr(String unarop, Any_expr any_expr) {
		setAny_expr(new UAny_expr(unarop, any_expr));
	}

	/**
	 * Create a unary operation expression with parentheses
	 * 
	 * @param unarop
	 * @param any_expr
	 *            an any-expression
	 */
	public Any_expr(String unarop, PAny_expr pany_expr) {
		setAny_expr(new UAny_expr(unarop, pany_expr));
	}

	/**
	 * Create any expression from intermediate any-expression.
	 * 
	 * @param iany_expr
	 */
	public Any_expr(IAny_expr any_expr) {
		setAny_expr(any_expr);
	}

	/**
	 * Create any expression from a variable reference.
	 * 
	 * @param varref
	 *            a variable reference.
	 */
	public Any_expr(Varref varref) {
		setAny_expr(varref);
	}

	public String toCode() throws RuleViolationException {
		return getAny_expr().toCode();
	}
}
