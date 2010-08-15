package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class Priority implements ToCode {
	private Const cnst;

	public Priority(Const cnst) {
		super();
		setCnst(cnst);
	}

	public Priority() {
		super();
	}

	public Const getCnst() {
		return cnst;
	}

	public void setCnst(Const cnst) {
		this.cnst = cnst;
	}

	@Override
	public String toCode() throws RuleViolationException {
		return Literal.PRIORITY + " " + cnst.toCode();
	}
}
