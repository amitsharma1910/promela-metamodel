package model.promela;

import exceptions.RuleViolationException;

public class Stmnt extends IStep {
	private IStmnt stmnt;

	public Stmnt(IStmnt stmnt) {
		super();
		setStmnt(stmnt);
	}

	/**
	 * Create an empty statement
	 */
	public Stmnt() {
	}

	public IStmnt getStmnt() {
		return stmnt;
	}

	public void setStmnt(IStmnt stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (stmnt == null)
			throw new RuleViolationException("A statement must not be empty");
		return getStmnt().toCode();
	}
}
