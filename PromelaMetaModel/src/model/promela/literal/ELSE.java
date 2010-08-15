package model.promela.literal;

import model.promela.IStmnt;

public class ELSE extends IStmnt {
	@Override
	public String toCode() {
		return Literal.ELSE;
	}
}
