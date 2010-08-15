package model.promela;

import exceptions.RuleViolationException;
import model.promela.literal.Literal;

/**
 * Object model for a print statement
 * 
 * @author Duc
 * 
 */
public class PrintStmnt extends IStmnt {
	private Str str = null;
	private Arg_lst arg_lst = null;

	public PrintStmnt(Str str, Arg_lst argLst) {
		super();
		setStr(str);
		setArg_lst(argLst);
	}

	/**
	 * create print statement to print a string
	 * 
	 * @param str
	 */
	public PrintStmnt(String str) {
		super();
		setStr(new Str(str));
	}

	public PrintStmnt() {
		// TODO Auto-generated constructor stub
	}

	public Str getStr() {
		return str;
	}

	public void setStr(Str str) {
		this.str = str;
	}

	public Arg_lst getArg_lst() {
		return arg_lst;
	}

	public void setArg_lst(Arg_lst argLst) {
		arg_lst = argLst;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (str == null)
			throw new RuleViolationException("A String must exist in a print statement");
		String ret = Literal.PRINT + " (" + str.toCode();
		if (getArg_lst() != null)
			ret = ret + "," + getArg_lst().toCode();
		return ret + ")";
	}
}
