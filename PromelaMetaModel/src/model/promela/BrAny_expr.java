package model.promela;

import exceptions.RuleViolationException;

/**
 * "Bracketed" any expression
 * 
 * @author Duc
 * 
 */
public class BrAny_expr implements ToCode {
	private Any_expr any_expr;

	public Any_expr getAny_expr() {
		return any_expr;
	}

	public void setAny_expr(Any_expr anyExpr) {
		any_expr = anyExpr;
	}

	public BrAny_expr(Any_expr anyExpr) {
		super();
		any_expr = anyExpr;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (any_expr == null)
			throw new RuleViolationException("any_expr must not be empty");
		return "[" + any_expr.toCode() + "]";
	}
}
