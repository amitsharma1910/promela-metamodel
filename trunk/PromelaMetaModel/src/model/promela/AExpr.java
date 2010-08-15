package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.op.Andor;

/**
 * Represent the 3rd choice of an expression
 * 
 * @author Duc
 * 
 */
public class AExpr extends IExpr {
	private Expr expr1;
	private Andor andor;
	private Expr expr2;

	public AExpr(Expr expr1, Andor andor, Expr expr2) {
		super();
		setExpr1(expr1);
		setAndor(andor);
		setExpr2(expr2);
	}

	public Expr getExpr1() {
		return expr1;
	}

	public void setExpr1(Expr expr1) {
		this.expr1 = expr1;
	}

	public Andor getAndor() {
		return andor;
	}

	public void setAndor(Andor andor) {
		this.andor = andor;
	}

	public Expr getExpr2() {
		return expr2;
	}

	public void setExpr2(Expr expr2) {
		this.expr2 = expr2;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (expr1 == null || andor == null || expr2 == null)
			throw new RuleViolationException("All expression and andor must not be empty");
		return getExpr1().toCode() + " " + getAndor().toCode() + " " + getExpr2().toCode();
	}
}
