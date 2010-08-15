package model.promela.literal;

import model.promela.IChanpoll;

public class FULL extends IChanpoll {
	@Override
	public String toCode() {
		return Literal.FULL;
	}
}
