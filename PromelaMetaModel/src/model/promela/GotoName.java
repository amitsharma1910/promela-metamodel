package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class GotoName extends IStmnt {
	private Name name;

	public GotoName(Name name) {
		super();
		setName(name);
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name == null)
			throw new RuleViolationException("A name must exist in a goto statement");
		return Literal.GOTO + " " + getName().toCode();
	}
}
