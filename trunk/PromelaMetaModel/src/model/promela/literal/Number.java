package model.promela.literal;

import exceptions.NotADigit;

public class Number extends AlphNum {
	private Character number;

	public Number() {

	}

	public Number(Character number) throws NotADigit {
		super();
		setNumber(number);

	}

	public Character getNumber() {
		return number;
	}

	public void setNumber(Character number) throws NotADigit {
		// check if the number is a digit
		if (!Character.isDigit(number))
			throw (new NotADigit());
		this.number = number;
	}

	@Override
	public String toCode() {
		return getNumber().toString();
	}
}
