package model.promela;

import exceptions.RuleViolationException;

/**
 * Labeled statement
 * 
 * @author Duc
 * 
 */
public class LbStmnt extends IStmnt {
	private Name name;
	private Stmnt stmnt;

	public LbStmnt(Name name, Stmnt stmnt) {
		super();
		setName(name);
		setStmnt(stmnt);
	}

	/**
	 * Create new statement with a label
	 * 
	 * @param name
	 * @param stmnt
	 */
	public LbStmnt(String name, Stmnt stmnt) {
		super();
		setName(new Name(name));
		setStmnt(stmnt);
	}

	/**
	 * Create new statement with a label
	 * 
	 * @param name
	 * @param istmnt
	 */
	public LbStmnt(String name, IStmnt istmnt) {
		super();
		setName(new Name(name));
		setStmnt(new Stmnt(istmnt));
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Stmnt getStmnt() {
		return stmnt;
	}

	public void setStmnt(Stmnt stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name == null || stmnt == null)
			throw new RuleViolationException("A name and a statment both must exist");
		return getName().toCode() + ":" + getStmnt().toCode();
	}
}
