package model.promela.literal.op;

import exceptions.RuleViolationException;

public class Andor extends IBinarop {
	private IAndor value;

	/**
	 * Create an andor with a string
	 * 
	 * @param andor
	 */
	public Andor(String andor) {
		if (andor.equals(OperLiteral.AND))
			value = new And();
		else if (andor.equals(OperLiteral.OR))
			value = new Or();
		else
			value = null;
	}

	public IAndor getValue() {
		return value;
	}

	public void setValue(IAndor value) {
		this.value = value;
	}

	public Andor(IAndor operator) {
		setValue(operator);
	}

	public String toCode() throws RuleViolationException {
		if (value == null)
			throw new RuleViolationException("andor must not be empty");
		return value.toCode();
	}
}
