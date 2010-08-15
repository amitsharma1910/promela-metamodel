package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

public class MConst extends IRecv_arg {
	private Const cnst;

	public MConst(Const cnst) {
		super();
		setCnst(cnst);
	}

	public Const getCnst() {
		return cnst;
	}

	public void setCnst(Const cnst) {
		this.cnst = cnst;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (cnst == null)
			throw new RuleViolationException("Constant must not be null");
		return Literal.MINUS + getCnst().toCode();
	}
}
