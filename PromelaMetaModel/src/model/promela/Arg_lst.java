package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

/**
 * Argument list.
 * 
 * @author Duc
 * 
 */
public class Arg_lst extends ISend_args {
	private List<Any_expr> exprs = new ArrayList<Any_expr>();

	/**
	 * Create an empty arg_lst.
	 */
	public Arg_lst() {
		super();
	}

	/**
	 * Create an arg_lst with one any_expr.
	 * 
	 * @param expr
	 */
	public Arg_lst(Any_expr expr) {
		super();
		addAnyExpr(expr);
	}

	/**
	 * Add an any_expr into this arg_lst/
	 * 
	 * @param anyExp
	 */
	public void addAnyExpr(Any_expr anyExp) {
		exprs.add(anyExp);
	}

	/**
	 * Add a variable reference to this argument list.
	 * 
	 * @param varName
	 */
	public void addVar(String varName) {
		exprs.add(new Any_expr(new Varref(varName)));
	}

	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	@Override
	public List<Varref> getVarrefs() {
		List<Varref> varrefs = new ArrayList<Varref>();
		for (int i = 0; i < exprs.size(); i++) {
			IAny_expr iany = exprs.get(i).getAny_expr();
			if (iany instanceof Varref) {
				varrefs.add((Varref) iany);
			}
		}
		return varrefs;
	}

	public List<Any_expr> getExprs() {
		return exprs;
	}

	public void setExprs(List<Any_expr> exprs) {
		this.exprs = exprs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (exprs.size() == 0)
			throw new RuleViolationException("There must be at least one expression");
		String ret = exprs.get(0).toCode();
		int size = exprs.size();
		for (int i = 1; i < size; i++)
			ret = ret + "," + exprs.get(i).toCode();
		return ret;
	}
}
