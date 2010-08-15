package model.promela;

import exceptions.RuleViolationException;

public class Str implements ToCode {
	private String str;

	public Str(String str) {
		super();
		setStr(str);
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (str == null)
			throw new RuleViolationException("A string must exist");
		return "\"" + str + "\"";
	}
}
