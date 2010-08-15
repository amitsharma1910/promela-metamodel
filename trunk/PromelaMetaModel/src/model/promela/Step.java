package model.promela;

import exceptions.RuleViolationException;

public class Step implements ToCode {
	private IStep istep;
	/**
	 * Is it a randomization step?
	 */
	private boolean randomization;

	public Step(IStep istep) {
		super();
		setIstep(istep);
	}

	/**
	 * Add a variable declaration with specified type and name
	 * 
	 * @param type
	 * @param name
	 */
	public void addVariableDecl(String type, String name) {
		Decl_lst ls = new Decl_lst();
		ls.addVariableDecl(type, name);
		WDecl_lst wls = new WDecl_lst(ls);
		istep = wls;
	}

	/**
	 * Create an empty step
	 */
	public Step() {
		// TODO Auto-generated constructor stub
	}

	public IStep getIstep() {
		return istep;
	}

	public void setIstep(IStep istep) {
		this.istep = istep;
	}

	public void setIstep(IStmnt istmnt) {
		this.istep = new Stmnt(istmnt);
	}

	public boolean isRandomization() {
		return randomization;
	}

	public void setRandomization(boolean randomization) {
		this.randomization = randomization;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (istep == null) {
			if (isRandomization() == false)
				throw new RuleViolationException("A step must not be empty");
			else
				return "";
		}
		return getIstep().toCode();
	}
}
