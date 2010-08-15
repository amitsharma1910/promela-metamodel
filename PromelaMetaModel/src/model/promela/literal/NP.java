package model.promela.literal;

import model.promela.IAny_expr;

/**
 * Non-progress system state
 * 
 * @author Duc
 * 
 */
public class NP extends IAny_expr {
	@Override
	public String toCode() {
		return Literal.NP;
	}
}
