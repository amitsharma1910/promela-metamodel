package model.promela;

import exceptions.RuleViolationException;

public class Uname extends ITypename implements ToCode {
	private Name name;

	public Uname() {
	}

	/**
	 * Create new user-defined
	 * 
	 * @param uname
	 *            a user-defined name
	 */
	public Uname(String uname) {
		setName(new Name(uname));
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String toCode() throws RuleViolationException {
		if (name == null)
			throw new RuleViolationException("Uname must not be empty");
		return name.toCode();
	}
}
