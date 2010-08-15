package model.promela.literal;

import model.promela.IChanpoll;

public class EMPTY extends IChanpoll {
	@Override
	public String toCode() {
		return Literal.EMPTY;
	}
}