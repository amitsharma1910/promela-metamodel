package model.promela;

import exceptions.RuleViolationException;

/**
 * name expression refers to pid.
 * 
 * @author Duc
 * 
 */
public class NAny_expr extends IAny_expr {
	private Name name1;
	private Any_expr expr;
	private Name name2;

	public NAny_expr(Name name1, Any_expr expr, Name name2) {
		super();
		setName1(name1);
		setExpr(expr);
		setName2(name2);
	}

	public Name getName1() {
		return name1;
	}

	public void setName1(Name name1) {
		this.name1 = name1;
	}

	public Any_expr getExpr() {
		return expr;
	}

	public void setExpr(Any_expr expr) {
		this.expr = expr;
	}

	public Name getName2() {
		return name2;
	}

	public void setName2(Name name2) {
		this.name2 = name2;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name1 == null || expr == null || name2 == null)
			throw new RuleViolationException("2 names and any_expr must exist at the same time");
		return getName1().toCode() + " [" + getExpr().toCode() + "]@" + getName2().toCode();
	}
}
