package model.promela.literal.op;

public class Lte extends IBinarop {
	private final String LTE = "<=";

	public String toCode() {
		return LTE;
	}
}