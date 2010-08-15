package model.promela;

import java.util.ArrayList;
import java.util.List;

import model.promela.literal.BREAK;
import model.promela.literal.Literal;
import util.PRUtils;
import exceptions.RuleViolationException;

public class Spec implements ToCode {

	// init proctype
	Init init = new Init();
	private List<Module> modules = new ArrayList<Module>();

	public Spec(List<Module> modules) {
		super();
		setModules(modules);
		addMtype("other");
	}

	public Spec() {
		super();
		addMtype("other");
	}

	/**
	 * Create new specification with a module
	 * 
	 * @param module
	 */
	public Spec(Module module) {
		super();
		addModule(module);
		addMtype("other");

	}

	/**
	 * Create new specification with a module
	 * 
	 * @param module
	 */
	public Spec(IModule module) {
		super();
		addModule(module);
		addMtype("other");

	}

	public void addModule(Module module) {
		getModules().add(module);
	}

	public void addModule(IModule imodule) {
		modules.add(new Module(imodule));
		if (imodule instanceof Proctype) {
			proctypes.add((Proctype) imodule);
		}
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public Init getInit() {
		return init;
	}

	public void setInit(Init init) {
		this.init = init;
	}

	/**
	 * Add a global variable declaration without an initial value.
	 * 
	 * @param varType
	 *            variable type
	 * @param varName
	 *            variable name
	 */
	public void addGlobalVariable(String varType, String varName) {
		One_decl varDec = new One_decl(varType, varName);
		addModule(new Decl_lst(varDec));
		gVarDecls.add(varDec);
	}

	/**
	 * Add a global variable declaration with an initial value.
	 * 
	 * @param varType
	 *            variable type
	 * @param varName
	 *            variable name
	 * @param initCnst
	 *            initial value
	 */
	public void addGlobalVariable(String varType, String varName, String initCnst) {
		One_decl varDec = new One_decl(varType, varName, initCnst);
		addModule(new Decl_lst(varDec));
		gVarDecls.add(varDec);
	}

	/**
	 * Add a global channel declaration with an initializer.
	 * 
	 * @param name
	 *            channel's name
	 * @param bufferCap
	 *            buffer's capacity
	 * @param messageTypes
	 *            types of messages in the channel
	 */
	public void addGlobalChannel(String name, int bufferCap, List<String> messageTypes) {
		One_decl odec = new One_decl(name, bufferCap, messageTypes);
		Decl_lst dl = new Decl_lst(odec);
		this.addModule(dl);
		gVarDecls.add(odec);
		gChanDecls.add(odec);
	}

	/**
	 * Add a field type into a typedef; create typedef if needed.
	 * 
	 * @param typedefname
	 * @param fieldtype
	 * @throws Exception
	 */
	public void addFieldTypedef(String typedefName, String fieldType, String fieldName) {
		// create type declaration
		Utype typedef = null;
		boolean defined = false;
		for (int i = 0; i < typedefDecls.size(); i++) {
			typedef = typedefDecls.get(i);
			if (typedef.getName().equals(typedefName))
				defined = true;
		}
		if (fieldType != null && fieldName != null) {
			if (defined) {
				// IF the typedef has been defined THEN
				// ADD the field
				// TODO: check if the field already exists
				typedef.addField(fieldType, fieldName);
			} else {
				// IF the typedef has been defined THEN
				// CREATE new typedef with the field
				typedef = new Utype(typedefName, new Decl_lst(new One_decl(fieldType, fieldName)));
				Module module = new Module(typedef);
				addModule(module);
				typedefDecls.add(typedef);
			}
		}
	}

	/**
	 * add a field into a typedef variable; create a typedef if needed OR change
	 * type of variable
	 * 
	 * @param varName
	 * @param fieldType
	 * @param fieldName
	 */
	public void addFieldtoVar(String varName, String fieldType, String fieldName) {
		// The variable should have been declared
		One_decl varDec = getVar(varName);
		// Get user defined type
		Utype ut = getUtype(varDec.getTypename());
		// create field in typedef declaration
		// if the typedef does not contain the field declaration.

		if (!fieldName.equals("")) {
			if (ut != null)
				ut.addField(fieldType, fieldName);
		} else { //primitive type
			String existingType = "";
			try {
				existingType = varDec.getTypename().toCode();
			} catch (RuleViolationException e) {
				e.printStackTrace();
			}
			//if the new field type is greater than existing type ==> replace
			if (PRUtils.compareTypes(fieldType, existingType) == 1)
				varDec.setTypename(fieldType);
		}
	}

	/**
	 * add a field into a typedef declaration of a utyped variable; create a
	 * typedef if needed; add a valid value for a mtype variable
	 * 
	 * @param varName
	 * @param fieldType
	 * @param fieldName
	 * @param value
	 *            a valid value for the field
	 */
	public void addFieldtoVar(String varName, String fieldType, String fieldName, String value) {
		// The variable should have been declared
		One_decl varDec = getVar(varName);
		// Get user defined type
		Utype ut = getUtype(varDec.getTypename());
		// create field in typedef declaration
		// if the typedef does not contain the field declaration.
		//		if (!ut.containsField(fieldType, fieldName)) {
		//			ut.addField(fieldType, fieldName);
		//		}
		ut.addField(fieldType, fieldName);
		if (fieldName.equals(Literal.MTYPE)) {
			addValidValuetoMtypeFieldWithVarname(varName, fieldName, value);
		}
	}

	/**
	 * Check if the mtype field presents in the list of mtype field
	 * 
	 * @param utypeName
	 * @param fieldName
	 * @return
	 */
	public boolean containsMtypeFields(String utypeName, String fieldName) {
		if (getMtypeUtypeField(utypeName, fieldName) != null)
			return true;
		return false;
	}

	/**
	 * Get a MtypeUtypeField for a utype name a field name
	 * 
	 * @param utypeName
	 * @param fieldName
	 * @return
	 */
	public MtypeUtypeField getMtypeUtypeField(String utypeName, String fieldName) {
		for (int i = 0; i < mtypeFields.size(); i++) {
			MtypeUtypeField mtField = mtypeFields.get(i);
			if (mtField.getUtypeName().equals(utypeName))
				if (mtField.getFieldName().equals(fieldName))
					return mtField;
		}
		return null;
	}

	/**
	 * Add a valid value to a mtype field in a utype.
	 * 
	 * @param varName
	 * @param fieldName
	 * @param value
	 */
	public void addValidValuetoMtypeFieldWithVarname(String varName, String fieldName, String value) {
		if (!fieldName.equals("")) {
			String varType = "";
			try {
				varType = getVar(varName).getTypename().toCode();
			} catch (RuleViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addValidValuetoMtypeFieldWithUtypename(varType, fieldName, value);
		}
	}

	/**
	 * Add a valid value to a mtype field in a utype.
	 * 
	 * @param utypeName
	 * @param fieldName
	 * @param value
	 */
	public void addValidValuetoMtypeFieldWithUtypename(String utypeName, String fieldName, String value) {
		MtypeUtypeField mtField = getMtypeUtypeField(utypeName, fieldName);
		if (mtField != null)// the list contains the field already
			mtField.addValidValue(value);
		else {// the list does not contain the field yet
			mtField = new MtypeUtypeField();
			mtField.setFieldName(fieldName);
			mtField.setUtypeName(utypeName);
			mtField.addValidValue(value);
			mtypeFields.add(mtField);
		}
	}

	/**
	 * Get live list of all valid values for a mtype field
	 * 
	 * @param p
	 *            the proctype that contains the varName.
	 * @param varName
	 * @param fieldName
	 * @return
	 */
	public List<String> getValidValuesMtypeFieldinVar(Proctype p, String varName, String fieldName) {
		String varType = "";
		One_decl varDec = new One_decl();
		// get global variable declaration
		if ((varDec = getVar(varName)) == null)
			// get local variable declaration
			varDec = p.getVar(varName);
		try {

			if ((varType = varDec.getTypename().toCode()) == null)

				varType = varDec.getTypename().toCode();

		} catch (RuleViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getValidValuesMtypeFieldinUtype(varType, fieldName);
	}

	/**
	 * Get all valid values for a mtype field
	 * 
	 * @param varName
	 * @param fieldName
	 * @return
	 */
	public List<String> getValidValuesMtypeFieldinUtype(String utypename, String fieldName) {
		List<String> validVals = new ArrayList<String>();

		for (MtypeUtypeField mt : mtypeFields) {
			if (mt.getUtypeName().equals(utypename) && mt.getFieldName().equals(fieldName))
				validVals = mt.getValidValues();
		}
		return validVals;
	}

	/**
	 * Get live list of all valid values for a mtype variable
	 * 
	 * @param p
	 * @param varName
	 * @return
	 */
	public List<String> getValidValuesMtypeVar(Proctype p, String varName) {
		List<String> validVals = new ArrayList<String>();
		String varType = "";
		One_decl varDec = new One_decl();
		// get global variable declaration
		if ((varDec = getVar(varName)) == null)
			// get local variable declaration
			varDec = p.getVar(varName);
		try {

			if ((varType = varDec.getTypename().toCode()) == null)

				varType = varDec.getTypename().toCode();

		} catch (RuleViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (MtypeUtypeField mt : mtypeFields) {
			if (mt.getUtypeName().equals(varType))
				validVals = mt.getValidValues();
		}
		return validVals;
	}

	/**
	 * add empty typedef
	 * 
	 * @param typename
	 * @throws Exception
	 */
	public void addTypedef(String typename) throws Exception {
		Utype typedef = new Utype(typename, new Decl_lst());
		Module module = new Module(typedef);
		addModule(module);
		typedefDecls.add(typedef);
	}

	/**
	 * Add a dummy proctype with no statement for later use
	 * 
	 * @param name
	 *            name of the proctype
	 * @throws Exception
	 * @return proctype added
	 */
	public Proctype addEmptyProctype(String name) {
		Proctype pr = new Proctype(name);
		// Debug: add a print statement for toCode()
		// pr.setSequence(new Sequence(new Step(new Stmnt(new
		// PrintStmnt("DEBUG")))));
		Module m = new Module(pr);
		this.addModule(m);
		proctypes.add(pr);
		return pr;
	}

	/**
	 * Add a message type into a channel. Channels have at most 2 mtypes.
	 * 
	 * @param channelVar
	 * @param messageType
	 */
	public void addMessageTypeToChannel(String channelVar, String messageType) {
		for (int i = 0; i < gChanDecls.size(); i++) {
			One_decl chanDec = gChanDecls.get(i);
			if (chanDec.containVarName(channelVar)) {
				chanDec.addMessageTypetoChannel(channelVar, messageType);
			}
		}
	}

	/**
	 * List of user-defined types
	 */
	List<Utype> typedefDecls = new ArrayList<Utype>();
	/**
	 * List of global variable declaration
	 */
	List<One_decl> gVarDecls = new ArrayList<One_decl>();
	/**
	 * List of global channel declaration
	 */
	List<One_decl> gChanDecls = new ArrayList<One_decl>();
	/**
	 * List of process types
	 */
	List<Proctype> proctypes = new ArrayList<Proctype>();
	/**
	 * List of message types names
	 */
	List<String> mtypeNames = new ArrayList<String>();

	List<MtypeUtypeField> mtypeFields = new ArrayList<MtypeUtypeField>();
	public int num = 1;

	/**
	 * Check whether a channel still has no message type
	 * 
	 * @param chanName
	 * @return
	 */
	public boolean hasNoMessageType(String chanName) {
		One_decl dec = getGlobalChanDecl(chanName);
		for (Ivar ivar : dec.getIvars()) {
			if (ivar.getCh_init().getTypenames().size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get a global channel declaration
	 * 
	 * @param chanName
	 * @return
	 */
	public One_decl getGlobalChanDecl(String chanName) {
		for (One_decl dec : gChanDecls) {
			for (Ivar ivar : dec.getIvars()) {
				try {
					if (ivar.getName().toCode().equals(chanName))
						return dec;
				} catch (RuleViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}



	/**
	 * Get the global declaration of a variable
	 * 
	 * @param varName
	 *            variable name
	 * @return
	 */
	public One_decl getVar(String varName) {
		if (!varName.equals("##opaque")) {
			// traverse all the variable list to get variable declaration
			int size = gVarDecls.size();
			for (int i = 0; i < size; i++) {
				One_decl o = gVarDecls.get(i);
				if (o.getIvars().get(0).getName().getName().equals(varName))
					return o;
			}
		} else {
			return new One_decl(PRUtils.OPAQUE, PRUtils.OPAQUE);
		}
		// if not exist --> return null
		return null;
	}

	/**
	 * Get user-defined declaration
	 * 
	 * @param typeName
	 *            a user-defined type name
	 * @return
	 */
	public Utype getUtype(String typeName) {
		int size = typedefDecls.size();
		for (int i = 0; i < size; i++) {
			Utype u = typedefDecls.get(i);
			if (u.getName().getName().equals(typeName))
				return u;
		}
		return null;
	}

	/**
	 * Get user-defined declaration
	 * 
	 * @param typeName
	 *            a user-defined type name
	 * @return
	 */
	public Utype getUtype(Typename typeName) {
		// find the utype in the typdef declaration
		int size = typedefDecls.size();
		for (int i = 0; i < size; i++) {
			Utype u = typedefDecls.get(i);
			try {
				if (u.getName().getName().equals(typeName.toCode()))
					return u;
			} catch (RuleViolationException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 * @return true if the name has been defined as a mtype
	 */
	public boolean hasMtype(String name) {
		return mtypeNames.contains(name);
	}

	/**
	 * Add a message type into this specification
	 * 
	 * @param name
	 */
	public void addMtype(String name) {
		if (!mtypeNames.contains(name)) {
			mtypeNames.add(name);
			addModule(new Mtype(name));
		}
	}

	/**
	 * Get the declaration of a proctype in a specification
	 * 
	 * @param proctypeName
	 *            the name of a proctype
	 * @return
	 */
	public Proctype getProctype(String proctypeName) {
		for (Proctype p : proctypes) {
			if (p.getName().getName().equals(proctypeName))
				return p;
		}
		return null;
	}

	// List<VarInfo> varInfos=new ArrayList<VarInfo>();
	/**
	 * Reorder module into the following weigh; the less weight will appear
	 * ealier: mtype:10, utype:20, decl_lst:35 ((variable):30,
	 * decl_lst(channels):40), proctype:50, init:60, never:70 and trace:80
	 */
	public void orderModules() {

		int size = modules.size();
		for (int p = 1; p < size; p++) {
			Module m = modules.get(p);
			int j = p;

			for (; j > 0 && m.getImodule().getPositionWeight() < modules.get(j - 1).getImodule().getPositionWeight(); j--)
				modules.set(j, modules.get(j - 1));
			modules.set(j, m);
		}

	}

	/**
	 * 
	 * @param sc
	 *            source code (ret)
	 */
	public String solveNameConflicts(String sc) {
		// FIND name conflict between var names and channel names
		List<String> cflNames = new ArrayList<String>();
		int mtsize = mtypeNames.size(), varsize = gVarDecls.size();

		for (int i = 0; i < mtsize; i++)
			for (int j = 0; j < varsize; j++) {
				One_decl varj = gVarDecls.get(j);
				for (int k = 0; k < varj.getIvars().size(); k++) {
					if (mtypeNames.get(i).equals(varj.getIvars().get(k).getName().getName()))
						cflNames.add(mtypeNames.get(i));
				}
			}
		// FOR EACH conflict name
		for (int i = 0; i < cflNames.size(); i++) {
			String cflname = cflNames.get(i);
			int nextidx = 0;// next index of the occurrence
			while ((nextidx = sc.indexOf(cflname, nextidx)) != -1) {
				// conflict name is not in a send statement and a mtype
				// declaration.
				if (sc.charAt(nextidx - 1) != '!' && sc.charAt(nextidx - 2) != '{') {
					String sub = sc.substring(nextidx).replaceFirst(cflname, cflname + PRUtils.VAR_SUFFIX);
					sc = sc.substring(0, nextidx) + sub;
				}
				nextidx += cflname.length();
			}
		}
		// FIND name conflict between var names and Utype names
		cflNames = new ArrayList<String>();
		int typedefNum = typedefDecls.size();

		// FOR EACH typedef
		for (int i = 0; i < typedefNum; i++)
			// FOR EACH variable declaration
			for (int j = 0; j < varsize; j++) {
				One_decl varj = gVarDecls.get(j);
				// FOR EACH variable name in a declaration
				for (int k = 0; k < varj.getIvars().size(); k++) {
					if (typedefDecls.get(i).getName().getName().equals(varj.getIvars().get(k).getName().getName()))
						cflNames.add(typedefDecls.get(i).getName().getName());
				}
			}
		// FOR EACH conflict name
		for (int i = 0; i < cflNames.size(); i++) {
			String cflname = cflNames.get(i);
			int nextidx = 0;// index of next occurrence
			while ((nextidx = sc.indexOf(cflname, nextidx)) != -1) {
				int nameL = cflname.length();
				// conflict name is not in a send statement, not a name of a
				// typedef, not a type in a variable declaration, not in a
				// channel declaration
				if (sc.charAt(nextidx - 1) != '!' && sc.charAt(nextidx - 2) != 'f'
						&& !((sc.charAt(nextidx - 1) == '\n') && sc.charAt(nextidx + nameL) == ' ')
						&& sc.charAt(nextidx - 2) != ',') {
					String sub = sc.substring(nextidx).replaceFirst(cflname, cflname + PRUtils.VAR_SUFFIX);
					sc = sc.substring(0, nextidx) + sub;
				}
				nextidx += nameL;
			}
		}
		// FOR EACH name conflict
		// REPLACE var name with var name + "_VAR":
		// FIND occurrence of the var name
		// IF the character before the occurrence 1 position is not ! (operation
		// of send statement) OR before the occurrence 2 positions is not {
		// (mtype declaration)
		// THEN REPLACE the occurrence with var name +"_VAR"
		// REPEAT the action UNTIL there is no more occurrence.
		return sc;
	}

	/**
	 * Add to sequence a routine to randomize a variable of a field's value
	 * 
	 * @param p
	 *            a proctype, for getting local declarations
	 * @param ranStep
	 * @param type
	 *            the type of the variable or field
	 * @param varFName
	 *            a variable
	 * @param fieldName
	 *            a field name
	 */
	public void randomizeVarField(Proctype p, Step ranStep, String type, String varName, String fieldName) {
		if (!mtypeNames.contains(varName)) { // avoid name conflict
			if (type.equals(Literal.MTYPE)) {// mtype

				ranStep.setIstep(new PrintStmnt("DEBUG"));
				List<String> values = getValidValuesMtypeFieldinVar(p, varName, fieldName);
				// CREATE a do-statement
				DoStmnt doS = new DoStmnt();

				for (String value : values) {
					// ADD assignment statements
					Sequence asn = new Sequence();
					asn.addStmnt(new Assign(varName, fieldName, value));
					doS.addOption(asn);
				}
				// ADD an assignment statement for "other"
				Sequence asn = new Sequence();
				asn.addStmnt(new Assign(varName, fieldName, "other"));
				doS.addOption(asn);

				// ADD break-statement
				BREAK br = new BREAK();
				Sequence breakSeq = new Sequence();
				breakSeq.addStmnt(br);
				doS.addOption(breakSeq);

				ranStep.setIstep(new Stmnt(doS));
			} else if (type.equals(Literal.BOOL) || type.equals(Literal.BIT)) {
				// boolean type

				// CREATE a do-statement
				DoStmnt doS = new DoStmnt();
				// ADD increment and decrement statements
				Sequence inc = new Sequence();
				inc.addStmnt(new Assign(varName, fieldName, Literal.TRUE));
				doS.addOption(inc);
				Sequence dec = new Sequence();
				dec.addStmnt(new Assign(varName, fieldName, Literal.FALSE));
				doS.addOption(dec);
				// ADD break-statement
				BREAK br = new BREAK();
				Sequence breakSeq = new Sequence();
				breakSeq.addStmnt(br);
				doS.addOption(breakSeq);

				// ADD the step next to the assign step above
				// into the input sequence
				int ranStepIdx = p.getSequence().getSteps().indexOf(ranStep);
				List<Step> steps = p.getSequence().getSteps();
				steps.add(ranStepIdx + 1, new Step(new Stmnt(doS)));
			} else if (type.equals(Literal.INT) || type.equals(Literal.SHORT) || type.equals(Literal.BYTE)) {
				// numeric type
				// let the var to be zero
				ranStep.setIstep(new Assign(varName, fieldName, new Const("0")));
				// CREATE a do-statement
				DoStmnt doS = new DoStmnt();
				// ADD increment and decrement statements
				Sequence inc = new Sequence();
				inc.addStmnt(new Assign(varName, fieldName, true));
				doS.addOption(inc);
				Sequence dec = new Sequence();
				dec.addStmnt(new Assign(varName, fieldName, false));
				doS.addOption(dec);
				// ADD break-statement
				BREAK br = new BREAK();
				Sequence breakSeq = new Sequence();
				breakSeq.addStmnt(br);
				doS.addOption(breakSeq);

				// ADD the step next to the assign step above
				// into the input sequence
				int ranStepIdx = p.getSequence().getSteps().indexOf(ranStep);
				List<Step> steps = p.getSequence().getSteps();
				steps.add(ranStepIdx + 1, new Step(new Stmnt(doS)));
			} else { // field of user-defined type:-?
				One_decl oneDec = getVar(varName);
				// getting global declaration
				if (oneDec == null) // getting local declaration
					oneDec = p.getVar(varName);
				Utype ut = getUtype(oneDec.getTypename());
				List<One_decl> fields = ut.getDeclst().getDecls();
				for (One_decl fieldDec : fields) {
					String fieldType = "";
					try {
						fieldType = fieldDec.getTypename().toCode();
					} catch (RuleViolationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<Ivar> ivars = fieldDec.getIvars();
					for (int j = 0; j < ivars.size(); j++) {
						randomizeVarField(p, ranStep, fieldType, varName, ivars.get(j).getName().getName());
					}
				}
			}
		}
	}

	/**
	 * Add randomize code for a variable
	 * 
	 * @param p
	 * @param ranStep
	 *            step in the sequence of the proctype.
	 * @param varName
	 */
	public void randomizeVar(Proctype p, Step ranStep, String varName) {
		One_decl varDec = getVar(varName);
		if (varDec == null)
			varDec = p.getVar(varName);
		if (varDec != null && !mtypeNames.contains(varName)) {
			// avoid name conflict
			String varType = "";
			try {
				varType = varDec.getTypename().toCode();
			} catch (RuleViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (varType.equals(Literal.MTYPE)) {// mtype
				ranStep.setIstep(new PrintStmnt("DEBUG"));
				List<String> values = getValidValuesMtypeVar(p, varName);
				// CREATE a do-statement
				DoStmnt doS = new DoStmnt();

				for (String value : values) {
					// ADD assignment statements
					Sequence asn = new Sequence();
					asn.addStmnt(new Assign(varName, value));
					doS.addOption(asn);
				}
				// ADD an assignment statement for "other"
				Sequence asn = new Sequence();
				asn.addStmnt(new Assign(varName, "other"));
				doS.addOption(asn);

				// ADD break-statement
				BREAK br = new BREAK();
				Sequence breakSeq = new Sequence();
				breakSeq.addStmnt(br);
				doS.addOption(breakSeq);

				ranStep.setIstep(new Stmnt(doS));

			} else if ((varType.equals(Literal.BIT) || varType.equals(Literal.INT) || varType.equals(Literal.SHORT) || varType
					.equals(Literal.BYTE))
					&& !mtypeNames.contains(varName)) {
				// numeric type
				// let the var to be zero
				ranStep.setIstep(new Assign(varName, new Const("0")));
				// CREATE a do-statement
				DoStmnt doS = new DoStmnt();
				// ADD increment and decrement statements
				Sequence inc = new Sequence();
				inc.addStmnt(new Assign(varName, true));
				doS.addOption(inc);
				Sequence dec = new Sequence();
				dec.addStmnt(new Assign(varName, false));
				doS.addOption(dec);
				// ADD break-statement
				BREAK br = new BREAK();
				Sequence breakSeq = new Sequence();
				breakSeq.addStmnt(br);
				doS.addOption(breakSeq);

				// ADD the step next to the assign step above
				// into the input sequence
				int ranStepIdx = p.getSequence().getSteps().indexOf(ranStep);
				List<Step> steps = p.getSequence().getSteps();
				steps.add(ranStepIdx + 1, new Step(new Stmnt(doS)));

			} else { // user-defined type
				Utype ut = getUtype(varDec.getTypename());
				List<One_decl> fields = ut.getDeclst().getDecls();
				// FOR Each inner field
				for (int i = 0; i < fields.size(); i++) {
					One_decl fieldDec = fields.get(i);
					String fieldType = "";
					try {
						fieldType = fieldDec.getTypename().toCode();
					} catch (RuleViolationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<Ivar> ivars = fieldDec.getIvars();
					for (int j = 0; j < ivars.size(); j++) {
						randomizeVarField(p, ranStep, fieldType, varName, ivars.get(j).getName().getName());
					}

				}
			}
		}
	}

	/**
	 * Substitute all randomization empty steps with true codes and unmark them
	 */
	public void randomizeVars() {
		for (int i = 0; i < proctypes.size(); i++) {
			Proctype p = proctypes.get(i);
			Sequence seq = p.getSequence();
			List<Step> steps = seq.getSteps();
			for (int j = 0; j < steps.size(); j++) {
				if (steps.get(j).isRandomization()) {
					Step ranStep = steps.get(j);
					// next step is a send step, according to generation
					// algorithm
					Step sendstep = steps.get(j + 1);
					if (sendstep.getIstep() instanceof Stmnt) {
						IStmnt ist = ((Stmnt) sendstep.getIstep()).getStmnt();
						if (ist instanceof Send) {
							Send send = (Send) ist;
							// get sent variables
							List<Varref> sentvars = send.getVarrefs();
							// FOR EACH sent variable, gen random codes for it
							for (Varref sentvar : sentvars) {
								randomizeVar(p, ranStep, sentvar.getName().getName());
							}
						}
					}
				}
			}
		}
	}

	public void addDummyFieldtoEmptyUtypes() {
		for (Utype ut : typedefDecls) {
			if (ut.getDeclst().getDecls().size() == 0)
				ut.getDeclst().getDecls().add(new One_decl(Literal.BYTE, PRUtils.DUMMY));
		}
	}

	@Override
	public String toCode() throws RuleViolationException {

		addDummyFieldtoEmptyUtypes();
		randomizeVars();

		if (modules.size() == 0)
			throw new RuleViolationException("A spec must contain at least one module");
		orderModules();

		String ret = modules.get(0).toCode();
		int size = modules.size();
		for (int i = 1; i < size; i++)
			ret = ret + "\n" + modules.get(i).toCode();

		ret = solveNameConflicts(ret);

		return ret;
	}

}
