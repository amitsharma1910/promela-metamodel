package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

/**
 * Argument list with parentheses
 * 
 * @author Duc
 * 
 */
public class PArg_lst extends ISend_args {
	private Any_expr any_expr;
	private Arg_lst arg_lst;

	public Any_expr getAny_expr() {
		return any_expr;
	}

	public void setAny_expr(Any_expr anyExpr) {
		any_expr = anyExpr;
	}

	public Arg_lst getArg_lst() {
		return arg_lst;
	}

	public void setArg_lst(Arg_lst argLst) {
		arg_lst = argLst;
	}

	public PArg_lst(Any_expr anyExpr, Arg_lst argLst) {
		super();
		setAny_expr(anyExpr);
		setArg_lst(argLst);
	}

	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	@Override
	public List<Varref> getVarrefs() {
		List<Varref> varrefs = new ArrayList<Varref>();
		IAny_expr iany = any_expr.getAny_expr();
		if (iany instanceof Varref) {
			varrefs.add((Varref) iany);
		}

		for (int i = 0; i < arg_lst.getVarrefs().size(); i++) {
			varrefs.add(arg_lst.getVarrefs().get(i));
		}
		return varrefs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (any_expr == null || arg_lst == null)
			throw new RuleViolationException("any_expr and arg_lst must not be empty");
		return getAny_expr().toCode() + " (" + getArg_lst().toCode() + ")";
	}

	@Override
	public void addVar(String varName) {
		if (any_expr == null)
			any_expr = new Any_expr(new Varref(varName));
		else
			arg_lst.addVar(varName);
	}
}
