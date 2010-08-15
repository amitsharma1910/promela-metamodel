package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

/**
 * Normal fifo send
 * 
 * @author Duc
 * 
 */
public class NSend extends ISend {
	private Varref varref;
	private Send_args sArgs;

	public NSend(Varref varref, Send_args sArgs) {
		super();
		setVarref(varref);
		setsArgs(sArgs);
	}

	/**
	 * Create a normal FIFO send statement
	 * 
	 * @param varName
	 *            Channel variable.
	 * @param sentVars
	 */
	public NSend(String varName, List<String> sentVars) {
		super();
		setVarref(varName);
		sArgs = new Send_args(new Arg_lst());
		for (int i = 0; i < sentVars.size(); i++) {
			sArgs.addVar(sentVars.get(i));
		}
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

	public void setVarref(String varref) {
		this.varref = new Varref(varref);
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
					"A normal fifo send must contain both varref and send arguments");
		return getVarref().toCode() + "!" + getsArgs().toCode();
	}
}
