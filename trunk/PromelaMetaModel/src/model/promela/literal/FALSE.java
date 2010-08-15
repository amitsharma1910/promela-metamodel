package model.promela.literal;

import model.promela.IConst;

public class FALSE extends IConst {
	private final String FALSE="false";
	public String toCode(){
		return FALSE;
	}
}
