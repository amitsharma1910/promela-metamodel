package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.Literal;

public class Proctype extends IModule {
	@Override
	protected int getPositionWeight() {
		return 50;
	}

	Active active = null;
	Name name = new Name();
	Decl_lst decl_lst = null;
	Priority priority = null;
	Enabler enabler = null;
	Sequence sequence = new Sequence();

	/**
	 * Create a dummy proctype with an empty body specified name.
	 * 
	 * @param name
	 *            the name of the created Proctype
	 * @throws Exception
	 */
	public Proctype(String name) {
		initNumber();
		setName(new Name(name));
		setSequence(new Sequence());
		// PrintStmnt pr = new PrintStmnt("proctype for partner link " + name);
		// Stmnt stm = new Stmnt(pr);
		// Step st = new Step(stm);
		// Sequence seq = new Sequence(st);
	}

	/**
	 * Create new proctype with a specified name and sequence
	 * 
	 * @param seq
	 */
	public Proctype(String name, Sequence seq) {
		super();
		initNumber();
		setName(new Name(name));
		setSequence(seq);
	}

	/**
	 * Create an empty proctype.
	 */
	public Proctype() {
		initNumber();
	}

	/**
	 * Add a step to the proctype
	 * 
	 * @param st
	 */
	public void addStep(Step st) {
		sequence.addStep(st);
	}

	/**
	 * Add a statement to the proctype
	 * 
	 * @param st
	 */
	public void addStmnt(Stmnt st) {
		addStep(new Step(st));
	}

	/**
	 * Add a "normal" statement to the proctype
	 * 
	 * @param st
	 */
	public void addStmnt(IStmnt st) {
		addStep(new Step(new Stmnt(st)));
	}

	/**
	 * Get local variable declaration.
	 * 
	 * @param varName
	 *            a local variable name.
	 * @return local variable declaration.
	 */
	public One_decl getVar(String varName) {
		List<Step> steps = sequence.getSteps();
		for (int i = 0; i < steps.size(); i++) {
			IStep istep = steps.get(i).getIstep();
			if (istep instanceof WDecl_lst) {
				Decl_lst declst = ((WDecl_lst) istep).getDecl_lst();
				List<One_decl> decs = declst.getDecls();
				for (int j = 0; j < decs.size(); j++) {
					One_decl dec = decs.get(j);
					if (dec.containVarName(varName))
						return dec;
				}
			}
		}
		return null;
	}

	@Override
	public String toCode() throws RuleViolationException {
		String ret = "";
		if (active != null)
			ret += active.toCode() + " ";
		ret += Literal.PROCTYPE;
		if (name.getName().equals("") || sequence.getSteps().size() == 0)
			throw new RuleViolationException(
					"A proctype must have a name and its body must not be empty");
		ret += " " + name.toCode() + " (";
		if (decl_lst != null)
			ret += decl_lst.toCode();
		ret += ")";
		if (priority != null)
			ret += " " + priority.toCode();
		if (enabler != null)
			ret += " " + enabler.toCode();
		ret += " {\n" + sequence.toCode() + "\n}";
		return ret;
	}

	/**
	 * Add a variable with specified type and name
	 * 
	 * @param type
	 * @param name
	 */
	public void addLocalVariable(String type, String name) {
		sequence.addVariableDecl(type, name);
	}

	/**
	 * Add a randomization step.
	 */
	public void addRandomizationStep() {
		sequence.addRandomizationStep();
	}

	private int number = 0;

	private void initNumber() {
		number = 0;
	}

	/**
	 * generate an unique number for variable.
	 * 
	 * @return
	 */
	public int getVarUniqueNumber() {
		number++;
		return number;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Decl_lst getDecl_lst() {
		return decl_lst;
	}

	public void setDecl_lst(Decl_lst declLst) {
		decl_lst = declLst;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Enabler getEnabler() {
		return enabler;
	}

	public void setEnabler(Enabler enabler) {
		this.enabler = enabler;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

}
