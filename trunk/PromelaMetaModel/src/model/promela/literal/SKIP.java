package model.promela.literal;

import model.promela.IConst;

public class SKIP extends IConst {
	private final String SKIP="skip";
	public String toCode(){
		return SKIP;
	}
}
