package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class AssertExpr extends IStmnt {
	private Expr expr;

	public AssertExpr(Expr expr) {
		super();
		setExpr(expr);
	}

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (expr == null)
			throw new RuleViolationException("There must be at least one expression in this ASSERT expression");
		return Literal.ASSERT + " " + getExpr().toCode();
	}
}
