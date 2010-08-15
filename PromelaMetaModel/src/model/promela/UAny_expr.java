package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.op.Unarop;

/**
 * Unary-operation expression
 * 
 * @author Duc
 * 
 */
public class UAny_expr extends IAny_expr {
	private Unarop unarop;
	private Any_expr any_expr;

	public Unarop getUnarop() {
		return unarop;
	}

	public void setUnarop(Unarop unarop) {
		this.unarop = unarop;
	}

	public Any_expr getAny_expr() {
		return any_expr;
	}

	public void setAny_expr(Any_expr anyExpr) {
		any_expr = anyExpr;
	}

	public UAny_expr(Unarop unarop, Any_expr anyExpr) {
		super();
		setUnarop(unarop);
		setAny_expr(anyExpr);
	}

	public UAny_expr(String unarop, Any_expr anyExpr) {
		super();
		setUnarop(new Unarop(unarop));
		setAny_expr(anyExpr);
	}

	/**
	 * Create unary expression with parentheses
	 * 
	 * @param unarop
	 * @param panyExpr
	 */
	public UAny_expr(String unarop, PAny_expr panyExpr) {
		super();
		setUnarop(new Unarop(unarop));
		setAny_expr(new Any_expr(panyExpr));
	}

	/**
	 * Create an unary operation expression from variable references
	 * 
	 * @param unarop
	 * @param expr
	 */
	public UAny_expr(String unarop, Varref expr) {
		setUnarop(new Unarop(unarop));
		setAny_expr(new Any_expr(expr));
	}

	public String toCode() throws RuleViolationException {
		if (unarop == null || any_expr == null)
			throw new RuleViolationException(
					"unary operator and expression must not be null");
		return unarop.toCode() + any_expr.toCode();
	}
}
