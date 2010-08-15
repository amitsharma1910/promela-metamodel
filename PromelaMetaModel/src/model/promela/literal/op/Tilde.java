package model.promela.literal.op;

public class Tilde extends IUnarop {
	private final String TILDE = "~";

	public String toCode() {
		return TILDE;
	}
}