package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

public class Decl_lst extends IModule {

	@Override
	protected int getPositionWeight() {
		return 35;
	}

	private List<One_decl> decls = new ArrayList<One_decl>();

	/**
	 * Create an empty declaration list.
	 */
	public Decl_lst() {
		// decls.add(new One_decl(Literal.MTYPE, "DEBUG"));
	}

	/**
	 * Create a declaration list with several declarations.
	 * 
	 * @param decls
	 * @throws Exception
	 */
	public Decl_lst(List<One_decl> decls) throws Exception {
		super();
		setDecls(decls);
	}

	/**
	 * Create a declaration list with one declaration.
	 * 
	 * @param decl
	 * @throws Exception
	 */
	public Decl_lst(One_decl decl) {
		super();
		addOneDecl(decl);
	}

	/**
	 * Add a variable declaration with specified type and name
	 * 
	 * @param type
	 * @param name
	 */
	public void addVariableDecl(String type, String name) {
		addOneDecl(new One_decl(type, name));
	}

	/**
	 * Add one more declaration into this list which already has at least 1
	 * declaration.
	 * 
	 * @param decl
	 *            Declaration to be added into this declaration list.
	 */
	public void addOneDecl(One_decl decl) {
		decls.add(decl);
	}

	public List<One_decl> getDecls() {
		return decls;
	}

	public void setDecls(List<One_decl> decls) {
		this.decls = decls;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (decls == null)
			throw new RuleViolationException("Declaration list must not be null");
		if (decls.size() == 0)
			throw new RuleViolationException("Declaration list must contains at least one declaration");
		String ret = "";
		ret += decls.get(0).toCode();
		for (int i = 1; i < decls.size(); i++)
			ret += ";\n" + decls.get(i).toCode();
		return ret;
	}
}
