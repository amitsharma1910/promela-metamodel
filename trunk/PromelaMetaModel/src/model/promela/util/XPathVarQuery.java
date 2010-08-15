package model.promela.util;

import java.io.StringReader;

import paser.xpath2.ParseException;
import paser.xpath2.SimpleNode;
import paser.xpath2.XPath;
import paser.xpath2.XPathTreeConstants;


/**
 * Represent XPath variable query to 1 field.
 * 
 * @author Duc
 * 
 */
public class XPathVarQuery {
	String varName;
	String partName;
	String xpathVarQuery;

	public XPathVarQuery() {
	}

	/**
	 * Create a representation of a typical query in <from> element. *Separate
	 * variable and part.
	 * 
	 * @param tranCond
	 */
	public XPathVarQuery(String xpathVarQuery) {
		xpathVarQuery = xpathVarQuery.trim();
		this.xpathVarQuery = xpathVarQuery;
		XPath parser = new XPath(new StringReader(xpathVarQuery));
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
		// SimpleNode lexprNode, rexprNode;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			SimpleNode child = (SimpleNode) node.jjtGetChild(i);
			if (child.getId() == XPathTreeConstants.JJTVARNAME) {
				processVarName(child);

			}
			processXPath(child);
		}
	}

	private void processVarName(SimpleNode node) {
		// node.dump("=*=>");
		for (int j = 0; j < node.jjtGetNumChildren(); j++) {
			SimpleNode subchild = (SimpleNode) node.jjtGetChild(j);
			if (subchild.getId() == XPathTreeConstants.JJTQNAME) {
				String qname = subchild.getValue();
				processFunctionQName(qname);
			}
			processVarName(subchild);
		}

	}

	/**
	 * fqn is like varName.part.
	 * 
	 * @param fqn
	 */
	private void processFunctionQName(String fqn) {
		fqn = fqn.trim();
		int dotPos = fqn.indexOf('.');
		if (dotPos == -1) {
			varName = fqn;
			partName = "";
		} else {
			varName = fqn.substring(0, dotPos);
			partName = fqn.substring(dotPos + 1);
		}
		// System.out.println("");
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	// public static void main(String args[]) {
	// XPathVarQuery a = new XPathVarQuery("$seller.auctionId.a");
	// }
}
