package util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import model.promela.Any_expr;
import model.promela.Assign;
import model.promela.Const;
import model.promela.Decl_lst;
import model.promela.DoStmnt;
import model.promela.Expr;
import model.promela.IExpr;
import model.promela.ITypename;
import model.promela.Module;
import model.promela.Mtype;
import model.promela.One_decl;
import model.promela.Options;
import model.promela.Proctype;
import model.promela.Sequence;
import model.promela.Spec;
import model.promela.Stmnt;
import model.promela.Utype;
import model.promela.Varref;
import model.promela.literal.BIT;
import model.promela.literal.BOOL;
import model.promela.literal.BYTE;
import model.promela.literal.CHAN;
import model.promela.literal.INT;
import model.promela.literal.Literal;
import model.promela.literal.MTYPE;
import model.promela.literal.SHORT;
import model.promela.util.Cond;
import paser.xpath2.SimpleNode;
import paser.xpath2.XPathTreeConstants;
import exceptions.RuleViolationException;

public class PRUtils {
	public static final String OPERATION = "operation";
	public static final String ENDLB = "end";
	public static final String VAR_SUFFIX = "_VAR";
	public static final String PLOUT_SUFFIX = "PL_OUT";
	public static final String PLIN_SUFFIX = "PL_IN";
	public static final String DUMMY = "DUMMY_FIELD";
	public static final String BPELGetVariableProperty = "bpel:getVariableProperty";
	public static final String OPAQUE = "OPAQUE";

	public static final int GREATER = 1;
	public static final int LESS = -1;
	public static final int EQUAL = 0;
	public static final int INCOMPARABLE = -2;

	/**
	 * convert Literal string into ITypename
	 * 
	 * @param typename
	 * @return
	 */
	// TODO: move these hard codes to a properties/XML file
	public static ITypename toITypename(String typename) {
		if (typename.equals(Literal.BIT))
			return new BIT();
		else if (typename.equals(Literal.BOOL))
			return new BOOL();
		else if (typename.equals(Literal.BYTE))
			return new BYTE();
		else if (typename.equals(Literal.CHAN))
			return new CHAN();
		else if (typename.equals(Literal.INT))
			return new INT();
		else if (typename.equals(Literal.MTYPE))
			return new MTYPE();
		else if (typename.equals(Literal.SHORT))
			return new SHORT();
		else
			return null;
	}

	/**
	 * convert an XML type to a Spin data type
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String cvtXMLTypeToPromelaType(QName type) {
		if (!type.getNamespaceURI().equals(XSCnst.xsNS))
			// throw new Exception("The type is not an XML type");
			return Literal.MTYPE;
		String lp = type.getLocalPart();
		if (lp.equals(XSCnst.BOOLEAN))
			return Literal.BOOL;
		else if (lp.equals(XSCnst.STRING))
			return Literal.MTYPE;
		else if (lp.equals(XSCnst.SHORT) || lp.equals(XSCnst.UNSIGNEDSHORT) || lp.equals(XSCnst.DURATION)
				|| lp.equals(XSCnst.DATETIME) || lp.equals(XSCnst.TIME) || lp.equals(XSCnst.DATE))
			return Literal.SHORT;
		else if (lp.equals(XSCnst.DECIMAL) || lp.equals(XSCnst.INTEGER) || lp.equals(XSCnst.INT)
				|| lp.equals(XSCnst.LONG) || lp.equals(XSCnst.NEGATIVEINTEGER) || lp.equals(XSCnst.NONPOSITIVEINTEGER)
				|| lp.equals(XSCnst.UNSIGNEDINT) || lp.equals(XSCnst.UNSIGNEDLONG) || lp.equals(XSCnst.POSITIVEINTEGER)
				|| lp.equals(XSCnst.NONEGATIVEINTEGER) || lp.equals(XSCnst.FLOAT) || lp.equals(XSCnst.DOUBLE))
			return Literal.INT;
		else
			return Literal.BYTE;
	}

	/**
	 * add variable to generated code
	 * 
	 * @param qname
	 * @param varName
	 *            variable's name
	 * @param spec
	 * @throws Exception
	 */
	public static void addVariable(QName qname, String varName, Spec spec) throws Exception {
		String stype = cvtXMLTypeToPromelaType(qname);
		Module module = new Module();
		if (stype.equals(Literal.MTYPE)) {
			Mtype m = new Mtype();
			m.addName(varName);
			module.setImodule(m);
		} else {
			One_decl dec = new One_decl(stype, varName);
			Decl_lst l = new Decl_lst(dec);
			module.setImodule(l);
		}
		spec.addModule(module);
	}

	/**
	 * Check if the name is a valid Promela name.
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isValidName(String name) {
		if (name == null || name.length() == 0)
			return false;
		if (!Character.isLetter(name.charAt(0)))
			return false;
		for (int i = 1; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isDigit(c) || Character.isLetter(c) || (c == '_')))
				return false;
		}
		return true;
	}

	/**
	 * Check whether the string contains numbers only.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containsAllNumber(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Check if the value is a valid constant value.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isValidCnst(String value) {
		if (!(value.equals(Literal.TRUE) || value.equals(Literal.FALSE) || value.equals(Literal.SKIP) || containsAllNumber(value)))
			return false;
		return true;
	}

	/**
	 * Generate a random routine for all part of a user-defined type variable
	 * 
	 * @return
	 */

	public static void genRandomize(Utype ut, Sequence seq) {

	}

	/**
	 * Generate a routine to generate a random numeric value.
	 * 
	 * @param randomType
	 *            the type for the random process.
	 * @param p
	 * @param ranVarName
	 *            name of the random variable.
	 * @return modified proctype.
	 */
	public static Proctype genRandomNumeric(String randomType, Proctype p, Sequence seq, String ranVarName) {
		// Create a short variable declaration

		// p.addLocalVariable(Literal.SHORT, v);
		p.addLocalVariable(randomType, ranVarName);
		// Create a do loop
		Options opts = new Options();
		opts.addDecIncOption(ranVarName, true);
		opts.addDecIncOption(ranVarName, false);
		DoStmnt loop = new DoStmnt(opts);
		loop.setOpts(opts);
		p.addStmnt(loop);
		return p;
	}

	/**
	 * Generate a routine to generate a random mtype value; add "other" value to
	 * the choice list
	 * 
	 * @param p
	 *            a proctype
	 * @param values
	 *            list of values to be choose.
	 * @param ranVarName
	 *            name of the random variable
	 * @return a proctype with added random routine.
	 */
	public static Proctype genRandomMtype(Proctype p, List<String> values, String ranVarName) {
		// Create a mtype variable declaration

		p.addLocalVariable(Literal.MTYPE, ranVarName);
		// Create a do loop
		Options opts = new Options();
		for (int i = 0; i < values.size(); i++) {
			opts.addOption(new Stmnt(new Assign(ranVarName, values.get(i))), null);
		}
		opts.addOption(new Stmnt(new Assign(ranVarName, "other")), null);
		DoStmnt loop = new DoStmnt(opts);
		loop.setOpts(opts);
		p.addStmnt(loop);
		return p;
	}

	/**
	 * Create a Promela expression from an XPath string. This method is able to
	 * process cases of XPath strings (transitionCondition, while condition) in
	 * standard examples only.
	 * 
	 * @param x
	 *            an expression
	 * @return
	 */
	public static Expr exprOf(String x) {
		Cond cond = new Cond(x);
		if (cond.getCondition() == null) {
			if (cond.getLvarFieldType().equals(Literal.MTYPE))
				return new Expr(new Varref(cond.getLvar(), cond.getLfield()), cond.getBinarop(), new Varref(cond
						.getRexp()));
			else
				return new Expr(new Varref(cond.getLvar(), cond.getLfield()), cond.getBinarop(), new Const(cond
						.getRexp()));
		} else
			return cond.getCondition();

	}

	/**
	 * Create a Promela expression from an XPath comparison expression.
	 * 
	 * @param node
	 *            a comparison node
	 * @return
	 */
	public static Any_expr exprOf(SimpleNode node) {
		Any_expr expr = null;
		if (node != null)
			if (node.getId() == XPathTreeConstants.JJTCOMPARISONEXPR) {
				String binarop = node.getValue();

				String qname = Cond.getQName(node);
				int dotPos = qname.indexOf('.');
				String lvar = "", lfield = "";
				if (dotPos != -1) {
					lvar = PRUtils.normalizeName(qname.substring(0, dotPos));
					lfield = qname.substring(dotPos + 1);
				} else {
					lvar = qname;
				}
				String rexpr = Cond.getStringLiteral(node);
				rexpr = rexpr.replaceAll("\'", "");
				if (PRUtils.containsAllNumber(rexpr))
					expr = new Any_expr(new Varref(lvar, lfield), binarop, new Const(rexpr));
				else
					expr = new Any_expr(new Varref(lvar, lfield), binarop, new Varref(rexpr));
			}
		return expr;
	}

	/**
	 * Create a NEGATED Promela expression from an XPath string. This method is
	 * able to process XPath string in standard examples only.
	 * 
	 * @param expr
	 *            an expression
	 * @return
	 */
	public static Expr nexprOf(String x) {
		Cond cond = new Cond(x);
		Expr e = new Expr();
		if (cond.getCondition() == null) {
			if (cond.getLvarFieldType().equals(Literal.MTYPE))
				e.setNExpr(new Varref(cond.getLvar(), cond.getLfield()), cond.getBinarop(), new Varref(cond.getRexp()));
			else
				e.setNExpr(new Varref(cond.getLvar(), cond.getLfield()), cond.getBinarop(), new Const(cond.getRexp()));
		} else {
			IExpr tmp = exprOf(x).getExpr();
			if (tmp instanceof Any_expr)
				e.setNExpr((Any_expr) tmp);//the returned result contains a condition represented by an any_expr
		}

		return e;
	}

	/**
	 * Convert the HTML operator to "normal" operator
	 * 
	 * @param operator
	 * @return
	 */
	public static String getBinarop(String operator) {
		if (operator.equals(XSCnst.LT))
			return "<";
		else if (operator.equals(XSCnst.GT))
			return ">";
		else
			return operator;
	}

	/**
	 * Make name Promela-compatible
	 * 
	 * @param name
	 */
	public static String normalizeName(String name) {
		String ret = name.trim();
		ret = ret.replaceAll(" ", "");
		ret = ret.replaceAll("\n", "");
		ret = ret.replaceAll("-", "_");
		ret = ret.replaceAll("!", "");
		ret = ret.replaceAll(" ", "_");
		ret = ret.replaceAll("\'", "");
		return ret;
	}

	/**
	 * Get a Promela data type of an integer.
	 * 
	 * @param val
	 * @return
	 */
	public static String typeOfInteger(int val) {
		if (val > Literal.MAXSHORT)
			return Literal.INT;
		else if (val > Literal.MAXBYTE)
			return Literal.SHORT;
		else
			return Literal.BYTE;
	}

	/**
	 * Get local part of a qname which has the form of prefix:localPart
	 * 
	 * @param qname
	 * @return
	 */
	public static String getLocalPart(String qname) {
		if (qname.indexOf(':') != -1)
			return qname.substring(qname.indexOf(':') + 1);
		return qname;
	}

	// @Test
	public final void testRandomMtype() {
		List<String> vals = new ArrayList<String>();
		vals.add("low");
		Spec s = new Spec(genRandomMtype(new Proctype("test"), vals, "r"));
		try {
			System.out.print(s.toCode());
		} catch (RuleViolationException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Check if cond has the form: bpel:getVariableProperty
	 * 
	 * @param cond
	 * @return
	 */
	public static boolean isGetVariableProperty(String cond) {
		cond = cond.trim();
		cond = cond.replaceAll("\n", "");
		if (cond.startsWith(PRUtils.BPELGetVariableProperty))
			return true;
		return false;
	}

	/**
	 * 
	 * @param type1
	 * @param type2
	 * @return 0 if equal, 1 if type1>type2, -1 if type1<type2 and -2 if they
	 *         are not comparable
	 */
	public static int compareTypes(String type1, String type2) {
		if (getWeightOfType(type1) == getWeightOfType(type2))
			return 0;
		else if (getWeightOfType(type1) < getWeightOfType(type2))
			return -1;
		else if (getWeightOfType(type1) > getWeightOfType(type2))
			return 1;
		else
			return -2;
	}

	/**
	 * Get weight of a Promela data type. BIT=BOOL < MTYPE<BYTE<SHORT<INT
	 * 
	 * @param type
	 * @return
	 */
	public static int getWeightOfType(String type) {
		if (type.equals(Literal.BIT) || type.equals(Literal.BOOL))
			return 10;
		else if (type.equals(Literal.MTYPE))
			return 20;
		else if (type.equals(Literal.BYTE))
			return 30;
		else if (type.equals(Literal.SHORT))
			return 40;
		else if (type.equals(Literal.INT))
			return 50;
		return 0;
	}

	public static String eliminatesNewLineCharacter(String str) {
		String ret = str;
		ret = ret.replaceAll("\n", "");
		ret = ret.trim();
		return ret;
	}
	// public final void testRandomNumeric() {
	// Spec s = new Spec(genRandomNumeric(Literal.SHORT, new Proctype("test"),
	// "r"));
	// try {
	// System.out.print(s.toCode());
	// } catch (RuleViolationException e) {
	// e.printStackTrace();
	// }
	// }
}
