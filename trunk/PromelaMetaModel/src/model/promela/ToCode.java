package model.promela;

import exceptions.RuleViolationException;

public interface ToCode {
	/**
	 * 
	 * @return the code that the class represent
	 */
	public String toCode() throws RuleViolationException;
}
