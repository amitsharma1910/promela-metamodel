package model.promela.literal.op;

public class Exclamation extends IUnarop {
	private final String EXCLAMATION = "!";

	public String toCode() {
		return EXCLAMATION;
	}
}