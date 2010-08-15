package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.Literal;

public class IfStmnt extends IStmnt {
	private Options opts = new Options();

	/**
	 * Add one more option
	 * 
	 * @param seq
	 */
	public void addOption(Sequence seq) {
		opts.addOption(seq);
	}

	/**
	 * Add one more option
	 * 
	 * @param guard
	 *            a guard expression
	 * @param subseq
	 *            a subsequence
	 */
	public void addOption(Expr guard, Sequence subseq) {
		// Combine guard and subseq into a new sequence
		Sequence seq = new Sequence();
		// Add guard into new sequence
		seq.addStep(new Step(new Stmnt(guard)));
		// add all steps in subseq into new sequence
		List<Step> steps = subseq.getSteps();
		for (int i = 0; i < steps.size(); i++) {
			seq.getSteps().add(steps.get(i));
		}
		opts.addOption(seq);
	}

	/**
	 * Add one option specified by a guard and list of statements
	 * 
	 * @param guard
	 * @param stmnts
	 */
	public void addOption(Expr guard, List<Stmnt> stmnts) {
		opts.addOption(guard, stmnts);
	}

	/**
	 * Add one option with one guard and one statement
	 * 
	 * @param guard
	 * @param stmnt
	 */
	public void addOption(Expr guard, Stmnt stmnt) {
		opts.addOption(guard, stmnt);
	}

	/**
	 * Create an empty if-statement.
	 */
	public IfStmnt() {
		super();
	}

	public IfStmnt(Options opts) {
		super();
		setOpts(opts);
	}

	public Options getOpts() {
		return opts;
	}

	public void setOpts(Options opts) {
		this.opts = opts;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (opts == null)
			throw new RuleViolationException(
					"At least one option must exist in an if statement");
		return Literal.IF + "\n" + getOpts().toCode() + "\n" + Literal.FI;
	}
}
