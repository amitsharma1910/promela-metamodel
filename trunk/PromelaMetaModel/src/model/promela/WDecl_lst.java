package model.promela;

import exceptions.RuleViolationException;

public class WDecl_lst extends IStep {
	private Decl_lst decl_lst;

	public WDecl_lst(Decl_lst declLst) {
		super();
		setDecl_lst(declLst);
	}

	/**
	 * Add a variable declaration with specified type and name
	 * 
	 * @param type
	 * @param name
	 */
	public void addVariableDecl(String type, String name) {
		decl_lst.addVariableDecl(type, name);
	}

	public Decl_lst getDecl_lst() {
		return decl_lst;
	}

	public void setDecl_lst(Decl_lst declLst) {
		decl_lst = declLst;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (decl_lst == null)
			throw new RuleViolationException("Declaration list must not be null");
		return decl_lst.toCode();
	}
}
