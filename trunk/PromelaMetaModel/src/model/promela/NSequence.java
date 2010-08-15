package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * Normal sequence
 * 
 * @author Duc
 * 
 */
public class NSequence extends IStmnt {
	private Sequence seq;

	public NSequence(Sequence seq) {
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
			throw new RuleViolationException("A sequence must exist");
		return Literal.OBRACE + getSeq().toCode() + Literal.OBRACE;
	}
}
