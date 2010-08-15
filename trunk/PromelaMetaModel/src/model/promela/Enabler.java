package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class Enabler implements ToCode {
	Expr expr;

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (expr == null)
			throw new RuleViolationException("The expression in an enabler must not be null");
		return Literal.PROVIDED + " (" + expr.toCode() + " )";
	}
}
