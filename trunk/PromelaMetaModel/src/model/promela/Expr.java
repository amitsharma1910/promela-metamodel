package model.promela;

import model.promela.literal.op.OperLiteral;
import exceptions.RuleViolationException;

/**
 * Expression
 * 
 * @author Duc
 * 
 */
public class Expr extends IStmnt {
	private IExpr expr;

	/**
	 * Create an empty expression
	 */
	public Expr() {
	}

	/**
	 * Create a binary operation expression.
	 * 
	 * @param varref1
	 *            the left-side variable reference.
	 * @param binarop
	 *            a binary operator.
	 * @param varref2
	 *            the right-side variable reference.
	 */
	public Expr(Varref varref1, String binarop, Varref varref2) {
		setExpr(new Any_expr(varref1, binarop, varref2));
	}

	/**
	 * Create a binary operation expression.
	 * 
	 * @param varref
	 *            the left-side variable reference.
	 * @param binarop
	 *            a binary operator.
	 * @param cnst
	 *            the right-side constant
	 */
	public Expr(Varref varref, String binarop, Const cnst) {
		setExpr(new Any_expr(varref, binarop, cnst));
	}

	/**
	 * Create a NEGATED binary operation expression
	 * 
	 * @param varref
	 *            the left-side variable reference.
	 * @param binarop
	 *            a binary operator.
	 * @param cnst
	 *            the right-side constant
	 */
	public void setNExpr(Varref varref, String binarop, Const cnst) {
		setExpr(new Any_expr(OperLiteral.EXCLAMATION, new PAny_expr(new Any_expr(varref, binarop, cnst))));
	}

	/**
	 * Create a NEGATED binary operation expression.
	 * 
	 * @param varref
	 *            the left-side variable reference.
	 * @param binarop
	 *            a binary operator.
	 * @param varref2
	 *            the right-side constant
	 */
	public void setNExpr(Varref varref, String binarop, Varref varref2) {
		setExpr(new Any_expr(OperLiteral.EXCLAMATION, new PAny_expr(new Any_expr(varref, binarop, varref2))));
	}

	/**
	 * Create a NEGATED binary operation expression.
	 * 
	 * @param expr
	 */
	public void setNExpr(Any_expr expr) {
		setExpr(new Any_expr(OperLiteral.EXCLAMATION, new PAny_expr(expr)));
	}

	public IExpr getExpr() {
		return expr;
	}

	public void setExpr(IExpr expr) {
		this.expr = expr;
	}

	public Expr(IExpr expr) {
		setExpr(expr);
	}

	/**
	 * Create an expression
	 * 
	 * @param any_exprone
	 *            one of the expression in the right side of the generation
	 */
	public Expr(IAny_expr any_expr) {
		setExpr(new Any_expr(any_expr));
	}

	public String toCode() throws RuleViolationException {
		if (expr == null)
			throw new RuleViolationException("expression must not be empty");
		return expr.toCode();
	}
}
