package model.promela.literal.op;

public class Or extends IAndor {
	private final String OR = "||";

	public String toCode() {
		return OR;
	}
}