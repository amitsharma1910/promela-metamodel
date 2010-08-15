package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.Literal;

public class Mtype extends IModule {
	@Override
	protected int getPositionWeight() {
		return 10;
	}

	private boolean haveESign = true;
	private List<Name> names = new ArrayList<Name>();

	/**
	 * Create mtype with a list of names
	 * 
	 * @param haveESign
	 * @param names
	 */
	public Mtype(List<Name> names) {
		super();
		setHaveESign(true);
		setNames(names);
	}

	/**
	 * Create a mtype with only one name
	 * 
	 * @param name
	 */
	public Mtype(String name) {
		super();
		setHaveESign(true);
		addName(name);
	}

	public Mtype() {
		super();
	}

	public void addName(String name) {
		names.add(new Name(name));
	}

	public boolean isHaveESign() {
		return haveESign;
	}

	public void setHaveESign(boolean haveESign) {
		this.haveESign = haveESign;
	}

	public List<Name> getNames() {
		return names;
	}

	public void setNames(List<Name> names) {
		this.names = names;
	}

	@Override
	public String toCode() throws RuleViolationException {
		String ret = Literal.MTYPE;
		if (isHaveESign())
			ret = ret + " = ";
		if (names.size() == 0)
			throw new RuleViolationException("At least one name must exist in an mtype declaration");
		ret = ret + "{ " + names.get(0).toCode();
		int size = names.size();
		for (int i = 1; i < size; i++)
			ret = ret + "," + names.get(i).toCode();
		return ret + " }";
	}
}
