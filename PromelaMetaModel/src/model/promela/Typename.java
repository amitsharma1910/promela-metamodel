package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.BIT;
import model.promela.literal.BOOL;
import model.promela.literal.BYTE;
import model.promela.literal.CHAN;
import model.promela.literal.INT;
import model.promela.literal.Literal;
import model.promela.literal.MTYPE;
import model.promela.literal.SHORT;

public class Typename implements ToCode {
	/**
	 * set primitive type name; this method does not concern about Uname
	 * 
	 * @param prType
	 *            a primitive type.
	 */
	public Typename(String prType) {
		if (prType.equals(Literal.BIT))
			setTypename(new BIT());
		else if (prType.equals(Literal.BOOL))
			setTypename(new BOOL());
		else if (prType.equals(Literal.BYTE))
			setTypename(new BYTE());
		else if (prType.equals(Literal.CHAN))
			setTypename(new CHAN());
		else if (prType.equals(Literal.INT))
			setTypename(new INT());
		else if (prType.equals(Literal.MTYPE))
			setTypename(new MTYPE());
		else if (prType.equals(Literal.SHORT))
			setTypename(new SHORT());
		else
			setTypename(new Uname(prType));
	}

	private ITypename typename;

	public ITypename getTypename() {
		return typename;
	}

	public void setTypename(ITypename typename) {
		this.typename = typename;
	}

	public String toCode() throws RuleViolationException {
		if (typename == null)
			throw new RuleViolationException("The type name must not be empty");
		return typename.toCode();
	}
}
