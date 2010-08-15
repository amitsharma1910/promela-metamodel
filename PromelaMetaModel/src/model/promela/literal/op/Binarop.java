package model.promela.literal.op;

import exceptions.RuleViolationException;
import model.promela.ToCode;

public class Binarop implements ToCode {
	public String toCode() throws RuleViolationException {
		if (value == null)
			throw new RuleViolationException("binarop must not be empty");
		return getValue().toCode();
	}

	public Binarop(IBinarop operator) {
		setValue(operator);
	}

	/**
	 * Create a binary operator with string
	 * 
	 * @param bo
	 */
	public Binarop(String bo) {
		if (bo.equals("&"))
			setValue(new Ampersand());
		else if (bo.equals(OperLiteral.AND) || bo.equals(OperLiteral.OR))
			setValue(new Andor(bo));
		else if (bo.equals("/"))
			setValue(new Divide());
		else if (bo.equals("==") || bo.equals("="))
			setValue(new Equal());
		else if (bo.equals(">"))
			setValue(new Gt());
		else if (bo.equals(">="))
			setValue(new Gte());
		else if (bo.equals("<<"))
			setValue(new LShift());
		else if (bo.equals("<"))
			setValue(new Lt());
		else if (bo.equals("<="))
			setValue(new Lte());
		else if (bo.equals("-"))
			setValue(new Minus());
		else if (bo.equals("*"))
			setValue(new Multiple());
		else if (bo.equals("!="))
			setValue(new NotEqual());
		else if (bo.equals("%"))
			setValue(new Percent());
		else if (bo.equals("|"))
			setValue(new Pipe());
		else if (bo.equals("+"))
			setValue(new Plus());
		else if (bo.equals("^"))
			setValue(new Power());
		else if (bo.equals(">>"))
			setValue(new RShift());
	}

	private IBinarop value;

	public IBinarop getValue() {
		return value;
	}

	public void setValue(IBinarop value) {
		this.value = value;
	}
}
