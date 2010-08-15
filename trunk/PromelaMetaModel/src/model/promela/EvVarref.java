package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * Evaluation receive argument
 * 
 * @author Duc
 * 
 */
public class EvVarref extends IRecv_arg {
	private Varref varref;

	public EvVarref(Varref varref) {
		super();
		setVarref(varref);
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null)
			throw new RuleViolationException("This receive argument must contain varref");
		return Literal.EVAL + " (" + getVarref().toCode() + ")";
	}
}
