package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * Unless statement
 * 
 * @author Duc
 * 
 */
public class UStmnt extends IStep {
	private Stmnt stmnt;
	private Stmnt ustmnt;

	public UStmnt(Stmnt stmnt, Stmnt ustmnt) {
		super();
		setStmnt(ustmnt);
		setUstmnt(ustmnt);
	}

	public Stmnt getStmnt() {
		return stmnt;
	}

	public void setStmnt(Stmnt stmnt) {
		this.stmnt = stmnt;
	}

	public Stmnt getUstmnt() {
		return ustmnt;
	}

	public void setUstmnt(Stmnt ustmnt) {
		this.ustmnt = ustmnt;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (stmnt == null || ustmnt == null)
			throw new RuleViolationException("The statement and unless statement must not be null");
		return getStmnt().toCode() + " " + Literal.UNLESS + " " + getUstmnt().toCode();
	}
}
