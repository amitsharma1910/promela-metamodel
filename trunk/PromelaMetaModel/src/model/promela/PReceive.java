package model.promela;

import exceptions.RuleViolationException;

/**
 * Poll with side-effect
 * 
 * @author Duc
 * 
 */
public class PReceive extends IReceive {
	private Varref varref;
	private Recv_args rargs;

	public PReceive(Varref varref, Recv_args rargs) {
		super();
		setVarref(varref);
		setRargs(rargs);
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public Recv_args getRargs() {
		return rargs;
	}

	public void setRargs(Recv_args rargs) {
		this.rargs = rargs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null || rargs == null)
			throw new RuleViolationException("varref and recev_args must both exist in a ditto");
		return getVarref().toCode() + "?<" + getRargs().toCode() + ">";
	}
}
