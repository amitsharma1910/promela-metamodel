package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.Literal;

public class DoStmnt extends IStmnt {
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
	 * Add one option specified by a guard and list of statements
	 * 
	 * @param guard
	 * @param stmnts
	 */
	public void addOption(Expr guard, List<Stmnt> stmnts) {
		opts.addOption(guard, stmnts);
	}

	/**
	 * Create an empty do-statement.
	 */
	public DoStmnt() {
		super();
	}

	public DoStmnt(Options opts) {
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
					"Do-structure must contain at least one option");
		return Literal.DO + "\n" + getOpts().toCode() + "\n" + Literal.OD;
	}
}
