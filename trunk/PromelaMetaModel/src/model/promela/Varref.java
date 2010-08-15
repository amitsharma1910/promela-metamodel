package model.promela;

import util.PRUtils;
import exceptions.RuleViolationException;

public class Varref extends IAny_expr {
	private Name name;
	private Any_expr expr;
	private Varref varref;

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setName(String name) {
		name = PRUtils.normalizeName(name);

		this.name = new Name(name);
	}

	public Any_expr getExpr() {
		return expr;
	}

	public void setExpr(Any_expr expr) {
		this.expr = expr;
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public Varref(Name name, Any_expr expr, Varref varref) {
		super();
		setName(name);
		setExpr(expr);
		setVarref(varref);
	}

	/**
	 * Create new variable reference with a name.
	 * 
	 * @param name
	 */
	public Varref(String name) {
		super();
		setName(new Name(name));
	}

	/**
	 * Create a reference to a field of a variable
	 * 
	 * @param varname
	 * @param field
	 */
	public Varref(String varname, String field) {
		super();
		setName(varname);
		if (field != "")
			setVarref(new Varref(field));
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name == null)
			throw new RuleViolationException("varref must contain a name");
		String ret = name.toCode();
		if (expr != null)
			ret = ret + " [" + expr.toCode() + "]";
		if (varref != null)
			ret = ret + "." + varref.toCode();
		return ret;
	}
}
