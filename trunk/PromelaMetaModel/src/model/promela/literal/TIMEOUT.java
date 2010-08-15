package model.promela.literal;

import model.promela.IAny_expr;

public class TIMEOUT extends IAny_expr {
	@Override
	public String toCode() {
		return Literal.TIMEOUT;
	}
}
