package model.promela.literal.op;

public class And extends IAndor {
	private final String AND = "&&";

	public String toCode() {
		return AND;
	}
}