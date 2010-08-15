package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

/**
 * Sorted send.
 * 
 * @author Duc
 * 
 */
public class SSend extends ISend {
	private Varref varref;
	private Send_args sArgs;

	public SSend(Varref varref, Send_args sArgs) {
		super();
		setVarref(varref);
		setsArgs(sArgs);
	}

	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	@Override
	public List<Varref> getVarrefs() {
		return sArgs.getVarrefs();
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public Send_args getsArgs() {
		return sArgs;
	}

	public void setsArgs(Send_args sArgs) {
		this.sArgs = sArgs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null || sArgs == null)
			throw new RuleViolationException(
					"A sorted send must contain both varref and send arguments");
		return getVarref().toCode() + "!!" + getsArgs().toCode();
	}
}
