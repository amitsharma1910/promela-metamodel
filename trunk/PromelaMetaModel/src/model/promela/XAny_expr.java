package model.promela;

import exceptions.RuleViolationException;

public class XAny_expr extends IAny_expr {
	private Any_expr expr1;
	private Any_expr expr2;
	private Any_expr expr3;

	public Any_expr getExpr1() {
		return expr1;
	}

	public void setExpr1(Any_expr expr1) {
		this.expr1 = expr1;
	}

	public Any_expr getExpr2() {
		return expr2;
	}

	public void setExpr2(Any_expr expr2) {
		this.expr2 = expr2;
	}

	public Any_expr getExpr3() {
		return expr3;
	}

	public void setExpr3(Any_expr expr3) {
		this.expr3 = expr3;
	}

	public XAny_expr(Any_expr expr1, Any_expr expr2, Any_expr expr3) {
		setExpr1(expr1);
		setExpr2(expr2);
		setExpr3(expr3);
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (expr1 == null || expr2 == null || expr3 == null)
			throw new RuleViolationException("All inner expression must not be null in this expression");
		return "(" + expr1.toCode() + "->" + expr2.toCode() + ":" + expr3.toCode() + ")";
	}
}
