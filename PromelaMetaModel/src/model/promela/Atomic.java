package model.promela;

import model.promela.literal.Literal;
import exceptions.RuleViolationException;

public class Atomic extends IStmnt {
	private Sequence seq = new Sequence();

	/**
	 * Create an empty atomic construct
	 */
	public Atomic() {
	}

	public Atomic(Sequence seq) {
		super();
		setSeq(seq);
	}

	/**
	 * Add one statement into the atomic construct.
	 * 
	 * @param stmnt
	 */
	public void addStmnt(Stmnt stmnt) {
		seq.addStep(new Step(stmnt));
	}

	/**
	 * Add one statement into the atomic construct.
	 * 
	 * @param stmnt
	 */
	public void addStmnt(IStmnt istmnt) {
		seq.addStep(new Step(new Stmnt(istmnt)));
	}

	/**
	 * Add one step into the atomic construct's sequence.
	 * 
	 * @param st
	 */
	public void addStep(Step st) {
		seq.addStep(st);
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
			throw new RuleViolationException("An atomic construct must contain at least one sequence");
		return Literal.ATOMIC + "{\n" + getSeq().toCode() + "\n}";
	}
}
