package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.AlphNum;
import model.promela.literal.Alpha;
import util.PRUtils;

public class Name implements ToCode {
	/**
	 * Create an empty name.
	 */
	Name() {
		name = "";
	}

	private Alpha alpha = null;
	private List<AlphNum> alphnums = new ArrayList<AlphNum>();

	public String toCode() throws RuleViolationException {
		if (!PRUtils.isValidName(name))
			throw new RuleViolationException("The name is not valid");
		return name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Name(String name) {
		setName(name);
	}

	// -----------------------------------------------------------------------------------
	// just in case code
	public Alpha getAlpha() {
		return alpha;
	}

	public void setAlpha(Alpha alpha) {
		this.alpha = alpha;
	}

	public List<AlphNum> getAlphnums() {
		return alphnums;
	}

	public void setAlphnums(List<AlphNum> alphnums) throws Exception {
		// the list must start with an alpha
		if (alpha == null)
			throw new Exception();
		this.alphnums = alphnums;
	}

	// append an alpha or number
	public void appendAlphNums(AlphNum alphnum) throws Exception {
		if (alpha == null)
			throw new Exception();
		this.alphnums.add(alphnum);
	}
}
