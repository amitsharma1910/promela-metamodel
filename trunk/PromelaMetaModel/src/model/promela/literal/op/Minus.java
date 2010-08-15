package model.promela.literal.op;

public class Minus extends IBinarop {
	private final String MINUS = "-";

	public String toCode() {
		return MINUS;
	}
}