package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

public class Ivar implements ToCode {
	private Name name;
	private Const cnst;
	private Any_expr any_expr;
	private Ch_init ch_init;

	public Ivar(Name name, Const cnst, Any_expr anyExpr, Ch_init chInit) {
		super();
		setName(name);
		setCnst(cnst);
		setAny_expr(anyExpr);
		setCh_init(chInit);
	}

	public Ivar(String name, Const cnst, Any_expr anyExpr, Ch_init chInit) {
		super();
		try {
			setName(new Name(name));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCnst(cnst);
		setAny_expr(anyExpr);
		setCh_init(chInit);
	}

	/**
	 * init var name only, there is no init value
	 * 
	 * @param name
	 */
	public Ivar(String name) {
		super();
		setName(new Name(name));
	}

	/**
	 * Initialize a variable by a constant
	 * 
	 * @param varName
	 * @param initCnst
	 */
	public Ivar(String varName, String initCnst) {
		super();
		setName(new Name(varName));
		setAny_expr(new Any_expr(new Const(initCnst)));
	}

	/**
	 * Initialize a buffered channel with type names
	 * 
	 * @param varName
	 * @param bufferCap
	 * @param typenames
	 */
	public Ivar(String varName, String bufferCap, List<String> typenames) {
		super();
		setName(new Name(varName));
		setCh_init(new Ch_init(bufferCap, typenames));
	}

	/**
	 * Initialize a buffered channel with type names
	 * 
	 * @param varName
	 * @param bufferCap
	 * @param typenames
	 */
	public Ivar(String varName, int bufferCap, List<String> typenames) {
		super();
		setName(new Name(varName));
		setCh_init(new Ch_init(String.valueOf(bufferCap), typenames));
	}

	/**
	 * Add one message type into the list of message types. If the type name has
	 * not been included in the channel.
	 * 
	 * @param mesType
	 */
	public void addMessageType(String mesType) {
		Typename type = new Typename(mesType);
		if (ch_init.getTypename(mesType) == null)
			ch_init.getTypenames().add(type);
	}

	public Name getName() {
		return name;
	}

	public void setName(String name) {
		this.name = new Name(name);
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Const getCnst() {
		return cnst;
	}

	public void setCnst(String cnst) {
		this.cnst = new Const(cnst);
	}

	public void setCnst(Const cnst) {
		this.cnst = cnst;
	}

	public Any_expr getAny_expr() {
		return any_expr;
	}

	public void setAny_expr(Any_expr anyExpr) {
		any_expr = anyExpr;
	}

	public Ch_init getCh_init() {
		return ch_init;
	}

	public void setCh_init(Ch_init chInit) {
		ch_init = chInit;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name == null)
			throw new RuleViolationException("There must exist at least one name in ivar");
		if (getAny_expr() != null && getCh_init() != null)
			throw new RuleViolationException("any_expr and ch_init must not exist at the same time");
		String ret = getName().toCode();
		if (getCnst() != null)
			ret = ret + " [" + getCnst().toCode() + "]";
		if (getAny_expr() != null)
			ret = ret + " = " + getAny_expr().toCode();
		if (getCh_init() != null)
			ret = ret + " = " + getCh_init().toCode();
		return ret;
	}
}
