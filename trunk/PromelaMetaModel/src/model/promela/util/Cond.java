package model.promela.util;

import java.io.StringReader;

import model.promela.Any_expr;
import model.promela.BinaAny_expr;
import model.promela.Expr;
import model.promela.literal.Literal;
import model.promela.literal.op.OperLiteral;
import paser.xpath2.ParseException;
import paser.xpath2.SimpleNode;
import paser.xpath2.XPath;
import paser.xpath2.XPathTreeConstants;
import util.PRUtils;

/**
 * Represent a transition condition for convenient use.
 * 
 * @author Duc
 * 
 */
public class Cond {
	String lvar = "";
	String lfield = "";
	String binarop = "";
	String rexp = "";
	String rVar = "";
	String rField = "";
	String tranCond = "";
	Expr condition;
	/**
	 * Type of the left-hand-side variable
	 */
	String lvarFieldType = "";

	/**
	 * Create a representation of a typical transition condition. * Analyze the
	 * transition Condition into left-hand-side variable, binary operator and
	 * right-hand-side constant. Especially for comparison expression
	 * 
	 * @param tranCond
	 */
	public Cond(String tranCond) {
		tranCond = tranCond.trim();
		tranCond = tranCond.replaceAll("\n", "");
		// tranCond = tranCond.replaceAll(" ", "");
		this.tranCond = tranCond;
		XPath parser = new XPath(new StringReader(tranCond));
		SimpleNode root = null;
		try {
			root = parser.XPath2();
		} catch (ParseException e) {
			System.err.println("Error happened when parsing XPath expression");
			e.printStackTrace();
		}
		processXPath(root);
	}

	/**
	 * Analyze XPath tokens.
	 * 
	 * @param node
	 */
	private void processXPath(SimpleNode node) {
		// node.dump("->");
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			SimpleNode child = (SimpleNode) node.jjtGetChild(i);
			if (child.getId() == XPathTreeConstants.JJTCOMPARISONEXPR
					|| child.getId() == XPathTreeConstants.JJTADDITIVEEXPR
					|| child.getId() == XPathTreeConstants.JJTMULTIPLICATIVEEXPR) {
				binarop = child.getValue();

				for (int j = 0; j < child.jjtGetNumChildren(); j++) {
					processPathExpr((SimpleNode) child.jjtGetChild(j));
				}
			}
			if (child.getId() == XPathTreeConstants.JJTANDEXPR) {
				binarop = OperLiteral.AND;
				Any_expr expr1 = PRUtils.exprOf((SimpleNode) child.jjtGetChild(0));
				Any_expr expr2 = PRUtils.exprOf((SimpleNode) child.jjtGetChild(1));
				Any_expr condition = new Any_expr(new BinaAny_expr(expr1, binarop, expr2));
				this.condition = new Expr(condition);
			}
			processXPath(child);
		}
	}

	private void processPathExpr(SimpleNode node) {
		// node.dump("=*=>");
		for (int j = 0; j < node.jjtGetNumChildren(); j++) {
			SimpleNode subchild = (SimpleNode) node.jjtGetChild(j);
			if (subchild.getId() == XPathTreeConstants.JJTQNAME) {
				String qname = subchild.getValue();
				int dotPos = qname.indexOf('.');

				if (dotPos != -1) {
					if (lvar.equals(""))
						lvar = PRUtils.normalizeName(qname.substring(0, dotPos));
					if (lfield.equals(""))
						lfield = qname.substring(dotPos + 1);
				} else {
					if (lvar.equals(""))
						lvar = qname;
				}
			} else if (subchild.getId() == XPathTreeConstants.JJTINTEGERLITERAL) {
				// numeric data types
				rexp = subchild.getValue();
				int val = Integer.parseInt(rexp);
				if (val > Literal.MAXSHORT)
					lvarFieldType = Literal.INT;
				else if (val > Literal.MAXBYTE)
					lvarFieldType = Literal.SHORT;
				else
					lvarFieldType = Literal.BYTE;
			} else if ((subchild.getId() == XPathTreeConstants.JJTDECIMALLITERAL)
					|| (subchild.getId() == XPathTreeConstants.JJTDOUBLELITERAL)) {
				rexp = String.valueOf(Literal.MAXBYTE);
				// treat the floating point value as byte
			} else if (subchild.getId() == XPathTreeConstants.JJTSTRINGLITERAL) {
				rexp = subchild.getValue();
				rexp = rexp.substring(1, rexp.length() - 1);
				lvarFieldType = Literal.MTYPE;
			} else if (subchild.getId() == XPathTreeConstants.JJTFUNCTIONCALL) {
				int i;
				boolean gettingVarProp = false;
				SimpleNode subNode = null;

				for (i = 0; i < subchild.jjtGetNumChildren(); i++) {
					subNode = (SimpleNode) subchild.jjtGetChild(i);
					if (subNode.getValue().startsWith(PRUtils.BPELGetVariableProperty)) {
						gettingVarProp = true;
						break;
					}

				}
				if (gettingVarProp) {
					subNode = (SimpleNode) subchild.jjtGetChild(i + 1);
					rVar = PRUtils.normalizeName(getStringLiteral(subNode));
					subNode = (SimpleNode) subchild.jjtGetChild(i + 2);
					rField = PRUtils.normalizeName(getStringLiteral(subNode));
					if (rField.indexOf(':') != -1)
						rField = PRUtils.getLocalPart(rField);
				}
			}
			processPathExpr(subchild);
		}

	}

	/**
	 * Getting StringLiteral value from this node
	 * 
	 * @param node
	 * @return
	 */
	public static String getStringLiteral(SimpleNode node) {
		String ret = "";
		if (node.jjtGetNumChildren() > 0)
			for (int i = 0; i < node.jjtGetNumChildren(); i++) {
				SimpleNode subNode = (SimpleNode) node.jjtGetChild(i);
				if (subNode != null) {
					if (subNode.getId() == XPathTreeConstants.JJTSTRINGLITERAL)
						ret = subNode.getValue();
					else
						ret = getStringLiteral(subNode);// return the first
					// occurrence
				}
			}
		return ret;

	}

	/**
	 * Getting StringLiteral value from this node
	 * 
	 * @param node
	 * @return
	 */
	public static String getQName(SimpleNode node) {
		String ret = "";
		if (node.jjtGetNumChildren() > 0)
			for (int i = 0; i < node.jjtGetNumChildren(); i++) {
				SimpleNode subNode = (SimpleNode) node.jjtGetChild(i);
				if (subNode != null) {
					if (subNode.getId() == XPathTreeConstants.JJTQNAME)
						return ret = subNode.getValue(); // get the first
					// occurrence
					else
						return ret = getQName(subNode);
				}
			}
		return ret;

	}

	/**
	 * 
	 * 
	 * @param tranCond
	 */
	public void setTranCond(String tranCond) {
		this.tranCond = tranCond;
	}

	public Cond(String lvar, String field, String op, String rexp) {
		super();
		this.lvar = lvar;
		this.lfield = field;
		this.binarop = op;
		this.rexp = rexp;
	}

	public String getTranCond() {
		return tranCond;
	}

	public String getLvar() {
		return PRUtils.getLocalPart(lvar);
	}

	public void setLvar(String lvar) {
		this.lvar = lvar;
	}

	public String getLfield() {
		return lfield;
	}

	public void setLfield(String field) {
		this.lfield = field;
	}

	public String getBinarop() {
		return binarop;
	}

	public void setBinarop(String binarop) {
		this.binarop = binarop;
	}

	public String getRexp() {
		return rexp;
	}

	public void setRexp(String rexp) {
		this.rexp = rexp;
	}

	public String getLvarFieldType() {
		return PRUtils.getLocalPart(lvarFieldType);
	}

	public void setLvarFieldType(String lvarType) {
		this.lvarFieldType = lvarType;
	}

	public String getrField() {
		return PRUtils.getLocalPart(rField);
	}

	public void setrField(String rField) {
		this.rField = rField;
	}

	public String getrVar() {
		return PRUtils.getLocalPart(rVar);
	}

	public void setrVar(String rVar) {
		this.rVar = rVar;
	}

	public Expr getCondition() {
		return condition;
	}

	public void setCondition(Expr condition) {
		this.condition = condition;
	}

	// public static void main(String args[]) {
	// Cond cond = new Cond("$risk.level!='low'");
	// System.out.println("");
	// }
}
