package model.promela.literal;

import model.promela.IConst;

public class TRUE extends IConst{
	private final String TRUE="true";
	public String toCode(){
		return TRUE;
	}
}
