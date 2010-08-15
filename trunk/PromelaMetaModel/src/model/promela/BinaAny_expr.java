package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.op.Binarop;

/**
 * Binary operator any_exprr
 * 
 * @author Duc
 * 
 */
public class BinaAny_expr extends IAny_expr {
	private Any_expr expr1;
	private Binarop binarop;
	private Any_expr expr2;

	public Any_expr getExpr1() {
		return expr1;
	}

	public void setExpr1(Any_expr expr1) {
		this.expr1 = expr1;
	}

	public Binarop getBinarop() {
		return binarop;
	}

	public void setBinarop(Binarop binarop) {
		this.binarop = binarop;
	}

	public Any_expr getExpr2() {
		return expr2;
	}

	public void setExpr2(Any_expr expr2) {
		this.expr2 = expr2;
	}

	/**
	 * Create an binary operation expression
	 * 
	 * @param any_expr1
	 * @param binarop
	 * @param any_expr2
	 */
	public BinaAny_expr(Any_expr expr1, Binarop binarop, Any_expr expr2) {
		setExpr1(expr1);
		setBinarop(binarop);
		setExpr2(expr2);
	}

	/**
	 * Create an binary operation expression
	 * 
	 * @param any_expr1
	 * @param binarop
	 * @param any_expr2
	 */
	public BinaAny_expr(Any_expr expr1, String binarop, Any_expr expr2) {
		setExpr1(expr1);
		setBinarop(new Binarop(binarop));
		setExpr2(expr2);
	}

	/**
	 * Create an binary operation expression
	 * 
	 * @param binaexpr1
	 * @param binarop
	 * @param binaexpr2
	 */
	public BinaAny_expr(BinaAny_expr binaexpr1, String binarop, BinaAny_expr binaexpr2) {
		setExpr1(new Any_expr(binaexpr1));
		setBinarop(new Binarop(binarop));
		setExpr2(new Any_expr(binaexpr2));
	}

	/**
	 * Create an binary operation expression from variable references
	 * 
	 * @param varref1
	 * @param binarop
	 * @param varref2
	 */
	public BinaAny_expr(Varref varref1, String binarop, Varref varref2) {
		setExpr1(new Any_expr(varref1));
		setBinarop(new Binarop(binarop));
		setExpr2(new Any_expr(varref2));
	}

	/**
	 * Create an binary operation expression from variable references
	 * 
	 * @param varref1
	 * @param binarop
	 * @param varref2
	 */
	public BinaAny_expr(String varref1, String binarop, String varref2) {
		setExpr1(new Any_expr(new Varref(varref1)));
		setBinarop(new Binarop(binarop));
		setExpr2(new Any_expr(new Varref(varref2)));
	}

	/**
	 * Create an binary operation expression from variable references
	 * 
	 * @param varref1
	 * @param binarop
	 * @param cnst
	 */
	public BinaAny_expr(Varref varref1, String binarop, Const cnst) {
		setExpr1(new Any_expr(varref1));
		setBinarop(new Binarop(binarop));
		setExpr2(new Any_expr(cnst));
	}

	/**
	 * Add new variable reference and a binary operator.
	 * 
	 * @param binarop
	 *            a binary operator.
	 * @param varref
	 *            a variable reference.
	 */
	public void addBinAnyExpr(String binarop, Varref varref) {
		if (expr2 == null) {
			setBinarop(new Binarop(binarop));
			setExpr2(new Any_expr(varref));
		} else {
			BinaAny_expr bae = new BinaAny_expr(expr2, binarop, new Any_expr(varref));
			setExpr2(new Any_expr(bae));
		}
	}

	/**
	 * Add new variable reference and a binary operator.
	 * 
	 * @param binarop
	 *            a binary operator.
	 * @param anyexpr
	 *            a any-expression.
	 */
	public void addBinAnyExpr(String binarop, Any_expr anyexpr) {
		if (expr2 == null) {
			setBinarop(new Binarop(binarop));
			setExpr2(anyexpr);
		} else {
			BinaAny_expr bae = new BinaAny_expr(expr2, binarop, anyexpr);
			setExpr2(new Any_expr(bae));
		}
	}

	/**
	 * Add new variable reference and a binary operator.
	 * 
	 * @param binarop
	 *            a binary operator.
	 * @param binaAnyexpr
	 *            a any-expression.
	 */
	public void addBinAnyExpr(String binarop, BinaAny_expr binaAnyexpr) {
		if (expr2 == null) {
			setBinarop(new Binarop(binarop));
			setExpr2(new Any_expr(binaAnyexpr));
		} else {
			BinaAny_expr bae = new BinaAny_expr(expr2, binarop, new Any_expr(binaAnyexpr));
			setExpr2(new Any_expr(bae));
		}
	}

	/**
	 * Add new variable reference and a binary operator.
	 * 
	 * @param binarop
	 *            a binary operator.
	 * @param varref
	 *            a variable reference.
	 */
	public void addBinAnyExpr(String binarop, String varref) {
		if (expr2 == null) {
			setBinarop(new Binarop(binarop));
			setExpr2(new Any_expr(new Varref(varref)));
		} else {
			BinaAny_expr bae = new BinaAny_expr(expr2, binarop, new Any_expr(new Varref(varref)));
			setExpr2(new Any_expr(bae));
		}
	}

	public String toCode() throws RuleViolationException {
		if (expr1 == null || binarop == null || expr2 == null)
			throw new RuleViolationException("2 any_exprs and binarop must not be empty");
		return expr1.toCode() + " " + binarop.toCode() + " " + expr2.toCode();
	}
}
