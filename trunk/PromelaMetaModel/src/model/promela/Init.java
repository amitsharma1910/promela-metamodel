package model.promela;

import model.promela.literal.Literal;
import exceptions.RuleViolationException;

public class Init extends IModule {
	@Override
	protected int getPositionWeight() {
		return 60;
	}

	private Priority priority;
	private Sequence seq = new Sequence();

	public Init(Priority priority, Sequence seq) {
		super();
		setPriority(priority);
		setSeq(seq);
	}

	public Init() {
		super();
	}

	public void addStmnt(IStmnt istmnt) {
		seq.addStmnt(istmnt);
	}

	public void addStmnt(Stmnt stmnt) {
		seq.addStmnt(stmnt);
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Sequence getSeq() {
		return seq;
	}

	public void setSeq(Sequence seq) {
		this.seq = seq;
	}

	@Override
	public String toCode() throws RuleViolationException {
		String ret = Literal.INIT;
		if (getPriority() != null)
			ret = ret + " " + getPriority().toCode();
		else
			ret = ret + " {\n" + getSeq().toCode() + "\n}";
		return ret;
	}
}
