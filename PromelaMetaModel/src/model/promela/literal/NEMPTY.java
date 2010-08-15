package model.promela.literal;

import model.promela.IChanpoll;

public class NEMPTY extends IChanpoll {
	@Override
	public String toCode() {
		return Literal.NEMPTY;
	}
}