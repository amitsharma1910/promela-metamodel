package model.promela;

import exceptions.RuleViolationException;

public class PExpr extends IExpr {
	private Expr expr;

	public PExpr(Expr expr) {
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
			throw new RuleViolationException("Expression must not be null");
		return "(" + getExpr().toCode() + ")";
	}
}
