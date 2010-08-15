package model.promela;

import exceptions.RuleViolationException;
import util.PRUtils;

public class Const extends IAny_expr {
	private String value;

	public String toCode() throws RuleViolationException {
		if (value == null)
			throw new RuleViolationException("The constant must not be empty");
		if (!PRUtils.isValidCnst(value))
			throw new RuleViolationException("The constant is not valid");
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Const(String iconst) {
		super();
		setValue(iconst);
	}

	public Const() {
	}
}
