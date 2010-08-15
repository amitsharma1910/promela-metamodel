package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

public class Send_args implements ToCode {
	private ISend_args arg;

	/**
	 * Add a variable name into send argument
	 * 
	 * @param varName
	 */
	public void addVar(String varName) {
		arg.addVar(varName);
	}

	public Send_args(ISend_args arg) {
		super();
		setArg(arg);
	}

	public ISend_args getArg() {
		return arg;
	}

	public void setArg(ISend_args arg) {
		this.arg = arg;
	}

	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	public List<Varref> getVarrefs() {
		return arg.getVarrefs();
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (arg == null)
			throw new RuleViolationException("Send argument must not be empty");
		return arg.toCode();
	}
}
