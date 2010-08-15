package model.promela;

import java.util.List;

import model.promela.literal.Literal;
import util.PRUtils;
import exceptions.RuleViolationException;

public class Utype extends IModule {
	@Override
	protected int getPositionWeight() {
		return 20;
	}

	private Name name;
	private Decl_lst declst;

	public Utype(Name name, Decl_lst declst) throws Exception {
		super();
		setName(name);
		setDeclst(declst);
	}

	/**
	 * create a typedef with specified name and declaration list
	 * 
	 * @param name
	 * @param declst
	 * @throws Exception
	 */
	public Utype(String name, Decl_lst declst) {
		super();
		setName(new Name(name));
		setDeclst(declst);
	}

	/**
	 * Add one field into typedef.
	 * 
	 * @param typename
	 *            Type of the field.
	 * @param name
	 *            Name of the field.
	 */
	public void addField(String fieldType, String fieldName) {
		// create type declaration
		// check if the field already presents
		// this Utype does not contain the field
		if (containsField(fieldType, fieldName) == 0) {
			One_decl typeDec = new One_decl(fieldType, fieldName);
			declst.addOneDecl(typeDec);
		} else if (containsField(fieldType, fieldName) == 1) {
			String existingFieldType = "";
			try {
				existingFieldType = getField(fieldName).getTypename().toCode();
			} catch (RuleViolationException e) {
				e.printStackTrace();
			}
			//comparing new field type and existing field type
			// if new field type "is greater" then replace it
			//TODO: REPLACE
			if (PRUtils.compareTypes(fieldType, existingFieldType) == 1) {
				getField(fieldName).setTypename(fieldType);
			}
		}
	}

	/**
	 * 
	 * @param fieldType
	 * @param fieldName
	 * @return 0,1 or 3:
	 *         fieldType fieldName returned value
	 *         0(not found) 0 0
	 *         0 1 (found) 1
	 *         1 0 -
	 *         1 1 3
	 * 
	 * 
	 * 
	 */
	public int containsField(String fieldType, String fieldName) {
		int ret = 0;//not found
		One_decl fieldDec = getField(fieldName);
		if (fieldDec != null) {
			try {
				if (fieldDec.containVarName(fieldName)) {
					ret = 1;// match var name only 
					if (fieldDec.getTypename().toCode().equals(fieldType))
						ret = 3; // match both type name and var name
				}
			} catch (RuleViolationException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean containsField(String fieldName) {
		One_decl fieldDec = getField(fieldName);
		if (fieldDec != null)
			return true;
		return false;
	}

	/**
	 * Get a field by field name
	 * 
	 * @param fieldName
	 * @return null if not found
	 */
	public One_decl getField(String fieldName) {
		List<One_decl> decls = declst.getDecls();
		for (int i = 0; i < decls.size(); i++) {
			One_decl o = decls.get(i);
			if (o.containVarName(fieldName))
				return o;
		}
		return null;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Decl_lst getDeclst() {
		return declst;
	}

	public void setDeclst(Decl_lst declst) {
		this.declst = declst;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (name == null || declst == null)
			throw new RuleViolationException("typedef must have a name and its declaration list must not be empty");
		return Literal.TYPEDEF + " " + getName().toCode() + " {\n" + getDeclst().toCode() + "\n}";
	}
}
