package model.promela.literal.op;

import exceptions.RuleViolationException;
import model.promela.ToCode;

public class Unarop implements ToCode {
	/**
	 * Create unary operator from a string
	 * 
	 * @param unarop
	 */
	public Unarop(String unarop) {
		if (unarop.equals(OperLiteral.EXCLAMATION))
			setOperator(new Exclamation());
		else if (unarop.equals(OperLiteral.NEGATE))
			setOperator(new Negation());
		else if (unarop.equals(OperLiteral.TILDE))
			setOperator(new Tilde());
		else
			setOperator(null);
	}

	public String toCode() throws RuleViolationException {
		if (operator == null)
			throw new RuleViolationException("unarop must not be empty");
		return getOperator().toCode();
	}

	public Unarop(IUnarop operator) {
		setOperator(operator);
	}

	private IUnarop operator;

	public IUnarop getOperator() {
		return operator;
	}

	public void setOperator(IUnarop operator) {
		this.operator = operator;
	}
}
