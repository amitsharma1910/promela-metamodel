package model.promela.literal.op;

public class Negation extends IUnarop {
	private final String NOT = "-";

	public String toCode() {
		return NOT;
	}
}