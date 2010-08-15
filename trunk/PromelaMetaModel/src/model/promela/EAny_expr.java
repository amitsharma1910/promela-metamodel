package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * Enabled expression
 * 
 * @author Duc
 * 
 */
public class EAny_expr extends IAny_expr {
	private Any_expr any_expr;

	public EAny_expr(Any_expr anyExpr) {
		super();
		setAny_expr(anyExpr);
	}

	public Any_expr getAny_expr() {
		return any_expr;
	}

	public void setAny_expr(Any_expr anyExpr) {
		any_expr = anyExpr;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (any_expr == null)
			throw new RuleViolationException("There must be at least one expression");
		return Literal.ENABLED + " (" + getAny_expr().toCode() + ")";
	}
}
