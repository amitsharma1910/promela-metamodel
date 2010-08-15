package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class Trace extends IModule {
	@Override
	protected int getPositionWeight() {
		return 80;
	}

	private Sequence seq;

	public Trace(Sequence seq) {
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
			throw new RuleViolationException("Sequence must not be null in a trace");
		return Literal.TRACE + " {" + getSeq().toCode() + "}";
	}

}
