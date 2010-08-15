package model.promela.literal;

import model.promela.IStmnt;

public class BREAK extends IStmnt {
	@Override
	public String toCode() {
		return Literal.BREAK;
	}
}
