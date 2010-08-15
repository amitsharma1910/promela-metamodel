package model.promela;

import exceptions.RuleViolationException;

public class Module implements ToCode {
	private IModule imodule;

	public Module(IModule imodule) {
		super();
		setImodule(imodule);
	}

	public Module() {
		super();
	}

	public IModule getImodule() {
		return imodule;
	}

	public void setImodule(IModule imodule) {
		this.imodule = imodule;
	}

	@Override
	public String toCode() throws RuleViolationException {
		// if ((imodule instanceof Utype) || (imodule instanceof Mtype)
		// || (imodule instanceof Decl_lst))
		// return getImodule().toCode() + ";";
		return getImodule().toCode();
	}
}
