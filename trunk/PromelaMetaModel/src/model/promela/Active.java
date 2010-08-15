package model.promela;

import exceptions.RuleViolationException;

public class Active implements ToCode {
	private final String ACTIVE = "active";

	public Active() {
		super();
	}

	public String getACTIVE() {
		return ACTIVE;
	}

	private ConstActive cnstAtv;

	public Active(ConstActive cnstA) throws Exception {
		setCnstAtv(cnstA);
	}

	public ConstActive getCnstAtv() {
		return cnstAtv;
	}

	public void setCnstAtv(ConstActive cnstAtv) {
		this.cnstAtv = cnstAtv;
	}

	public String toCode() throws RuleViolationException {
		if (cnstAtv != null)
			return ACTIVE + " [" + cnstAtv.toCode() + "]";
		else
			return ACTIVE;
	}
}
