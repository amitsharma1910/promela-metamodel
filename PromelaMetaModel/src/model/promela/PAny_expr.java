package model.promela;

import exceptions.RuleViolationException;

/**
 * Any expression with parentheses
 * 
 * @author Duc
 * 
 */
public class PAny_expr extends IAny_expr {
	private Any_expr expr;

	public Any_expr getExpr() {
		return expr;
	}

	public void setExpr(Any_expr expr) {
		this.expr = expr;
	}

	public PAny_expr(Any_expr expr) {
		setExpr(expr);
	}

	public String toCode() throws RuleViolationException {
		if (expr == null)
			throw new RuleViolationException("expression must not be empty");
		return "(" + getExpr().toCode() + ")";
	}
}
