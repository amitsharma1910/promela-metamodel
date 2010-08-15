package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class RunStmnt extends IAny_expr {
	private Name name;
	private Arg_lst arglst;
	private Priority priority;

	public RunStmnt(Name name, Arg_lst arglst, Priority priority) {
		super();
		setName(name);
		setArglst(arglst);
		setPriority(priority);
	}

	/**
	 * Create a run statement with a name
	 * 
	 * @param name
	 */
	public RunStmnt(String name) {
		super();
		setName(new Name(name));
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Arg_lst getArglst() {
		return arglst;
	}

	public void setArglst(Arg_lst arglst) {
		this.arglst = arglst;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name == null)
			throw new RuleViolationException("A run statement must contain a name");
		String ret = Literal.RUN + " " + getName().toCode() + "(";
		if (getArglst() != null)
			ret = ret + getArglst().toCode();
		ret = ret + ")";
		if (getPriority() != null)
			ret = ret + " " + getPriority().toCode();
		return ret;
	}
}
