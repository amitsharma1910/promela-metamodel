package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.Literal;

public class XRVarref extends IStep {
	private List<Varref> varrefs = new ArrayList<Varref>();

	public XRVarref(Varref varref, List<Varref> varrefs) {
		super();
		setVarrefs(varrefs);
	}

	/**
	 * Add one more varref into this step.
	 * 
	 * @param v
	 */
	public void addVarref(Varref v) {
		varrefs.add(v);
	}

	public List<Varref> getVarrefs() {
		return varrefs;
	}

	public void setVarrefs(List<Varref> varrefs) {
		this.varrefs = varrefs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varrefs.size() == 0)
			throw new RuleViolationException("The step must contain at least one varref");
		String ret = Literal.XR + " " + varrefs.get(0).toCode();
		for (int i = 1; i < varrefs.size(); i++)
			ret = ret + "," + varrefs.get(i).toCode();
		return ret;
	}
}
