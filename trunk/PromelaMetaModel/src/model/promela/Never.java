package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class Never extends IModule {
	@Override
	protected int getPositionWeight() {
		return 70;
	}

	private Sequence seq;

	public Never(Sequence seq) {
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
			throw new RuleViolationException("Sequence must not be null");
		return Literal.NEVER + " {" + getSeq().toCode() + "}";
	}

}
