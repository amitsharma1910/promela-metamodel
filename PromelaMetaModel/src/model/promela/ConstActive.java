package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class ConstActive implements ToCode {
	public String toCode() throws RuleViolationException {
		return OBRACKET + cnt.toCode() + CBRACKET;
	}

	private final char OBRACKET = Literal.OBRACKET;
	private final char CBRACKET = Literal.CBRACKET;
	private Const cnt;

	public Const getCnt() {
		return cnt;
	}

	public void setCnt(Const cnt) throws Exception {
		if (cnt == null)
			throw new Exception();
		this.cnt = cnt;
	}

	public ConstActive(Const cnt) throws Exception {
		setCnt(cnt);
	}
}
