package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

public class Send extends IStmnt {
	private ISend isend;

	public Send(ISend isend) {
		super();
		setIsend(isend);
	}

	/**
	 * Create a normal send statement.
	 * 
	 * @param varName
	 *            Channel variable.
	 * @param sentVars
	 *            List of transfered variables.
	 */
	public Send(String varName, List<String> sentVars) {
		setIsend(new NSend(varName, sentVars));
	}

	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	public List<Varref> getVarrefs() {
		return isend.getVarrefs();
	}

	public ISend getIsend() {
		return isend;
	}

	public void setIsend(ISend isend) {
		this.isend = isend;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (isend == null)
			throw new RuleViolationException(
					"A send statement must not be empty");
		return isend.toCode();
	}
}
