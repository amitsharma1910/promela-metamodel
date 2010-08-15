package model.promela.util;

import util.PRUtils;

/**
 * Analyzing function calls which are like getVariableProperty('a', 'c:b')
 * 
 * @author Duc
 * 
 */
public class GetVariableProperty {
	String var = "";
	String property = "";

	/**
	 * Analyzing function calls which are like getVariableProperty('a', 'c:b')
	 * 
	 * @param functionCall
	 */
	public GetVariableProperty(String funcCall) {
		String args = funcCall.substring(funcCall.indexOf('('), funcCall.indexOf(')'));
		int c1, c2, c3, c4;
		c1 = args.indexOf('\'');
		c2 = args.indexOf('\'', c1 + 1);
		c3 = args.indexOf('\'', c2 + 1);
		c4 = args.indexOf('\'', c3 + 1);
		var = PRUtils.getLocalPart(args.substring(c1 + 1, c2));
		property = PRUtils.getLocalPart(args.substring(c3 + 1, c4));
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
