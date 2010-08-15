package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

public class Options implements ToCode {
	private List<Sequence> seqs = new ArrayList<Sequence>();

	public Options() {
	}

	public Options(Sequence seq) throws Exception {
		super();
		addOption(seq);
	}

	/**
	 * Add one option.
	 * 
	 * @param guard
	 *            a guard expression.
	 * @param stmnts
	 *            a list of statements, which may be empty.
	 */
	public void addOption(Expr guard, List<Stmnt> stmnts) {
		Sequence opt = new Sequence();
		opt.addStmnt(new Stmnt(guard));
		if (stmnts != null) {
			for (int i = 0; i < stmnts.size(); i++) {
				opt.addStmnt(stmnts.get(i));
			}
		}
		seqs.add(opt);
	}

	/**
	 * Add one option with one guard and one statement
	 * 
	 * @param guard
	 * @param stmnt
	 */
	public void addOption(Expr guard, Stmnt stmnt) {
		Sequence opt = new Sequence();
		opt.addStmnt(new Stmnt(guard));
		if (stmnt != null) {
			opt.addStmnt(stmnt);
		}
		seqs.add(opt);
	}

	/**
	 * Add one option.
	 * 
	 * @param guard
	 *            a guard statement.
	 * @param stmnts
	 *            a list of statements, which may be empty.
	 */
	public void addOption(Stmnt guard, List<Stmnt> stmnts) {
		Sequence opt = new Sequence();
		opt.addStep(new Step(guard));
		if (stmnts != null) {
			for (int i = 0; i < stmnts.size(); i++) {
				opt.addStep(new Step(stmnts.get(i)));
			}
		}
		seqs.add(opt);
	}

	/**
	 * Add a decrement or increment statement as guard; especially for random
	 * value generation
	 * 
	 * @param varName
	 * @param increment
	 */
	public void addDecIncOption(String varName, boolean increment) {
		Sequence opt = new Sequence();
		opt.addStep(new Step(new Stmnt(new Assign(varName, increment))));
		seqs.add(opt);
	}

	/**
	 * Add one more option
	 * 
	 * @param seq
	 */
	public void addOption(Sequence seq) {
		seqs.add(seq);
	}

	public List<Sequence> getSeqs() {
		return seqs;
	}

	public void setSeqs(List<Sequence> seqs) {
		this.seqs = seqs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (seqs.size() == 0)
			throw new RuleViolationException();
		String ret = ":: " + seqs.get(0).toCode();
		int size = seqs.size();
		for (int i = 1; i < size; i++)
			ret = ret + "\n:: " + seqs.get(i).toCode();
		return ret;
	}
}
