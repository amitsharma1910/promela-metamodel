package model.promela.literal;

import model.promela.ITypename;

public class INT extends ITypename {

	public String toCode() {
		return Literal.INT;
	}
}