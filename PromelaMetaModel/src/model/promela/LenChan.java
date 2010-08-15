package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * The length of a channel
 * 
 * @author Duc
 * 
 */
public class LenChan extends IAny_expr {
	private Varref varref;

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public LenChan(Varref varref) {
		super();
		setVarref(varref);
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null)
			throw new RuleViolationException("varref must not be null");
		return Literal.LEN + " (" + varref.toCode() + ")";
	}
}
