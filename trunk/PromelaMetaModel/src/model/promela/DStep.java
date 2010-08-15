package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class DStep extends IStmnt {
	private Sequence seq;

	public DStep(Sequence seq) {
		super();
		setSeq(seq);
	}

	public Sequence getSeq() {
		return seq;
	}

	public void setSeq(Sequence seq) {
		this.seq = seq;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (seq == null)
			throw new RuleViolationException("A sequence must exist in a d_step");
		return Literal.D_STEP + " " + Literal.OBRACE + getSeq().toCode() + Literal.OBRACE;
	}
}
