package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * PC value
 * 
 * @author Duc
 * 
 */
public class PCvalue extends IAny_expr {
	private Any_expr expr;

	public Any_expr getExpr() {
		return expr;
	}

	public void setExpr(Any_expr expr) {
		this.expr = expr;
	}

	public PCvalue(Any_expr expr) {
		super();
		setExpr(expr);
	}

	public String toCode() throws RuleViolationException {
		if (expr == null)
			throw new RuleViolationException("At least one any_expr exists");
		return Literal.PC_VALUE + " (" + expr.toCode() + ")";
	}
}
