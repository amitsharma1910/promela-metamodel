package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class C_Track extends IStmnt {
	private String code;

	public C_Track(String code) {
		super();
		setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (code == null)
			throw new RuleViolationException("code must not be empty");
		return Literal.C_TRACK + " {" + code + "}";
	}
}
