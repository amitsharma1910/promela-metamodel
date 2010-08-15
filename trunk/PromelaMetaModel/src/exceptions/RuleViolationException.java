package exceptions;

public class RuleViolationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8523567260184926540L;

	public RuleViolationException() {
		super();
	}

	public RuleViolationException(String str) {
		super(str);
	}
}
