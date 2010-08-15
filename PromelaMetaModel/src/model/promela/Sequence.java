package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

public class Sequence implements ToCode {
	private List<Step> steps = new ArrayList<Step>();

	public Sequence() {
		super();
	}

	/**
	 * Create a sequence with one step.
	 * 
	 * @param step
	 */
	public Sequence(Step step) {
		super();
		addStep(step);
	}

	/**
	 * Add one more step into the sequence
	 * 
	 * @param st
	 */
	public void addStep(Step st) {
		steps.add(st);
	}

	/**
	 * Add one more statement in the form of step into the sequence
	 * 
	 * @param st
	 *            a statement
	 */
	public void addStmnt(Stmnt st) {
		steps.add(new Step(st));
	}

	/**
	 * Add one more statement in the form of step into the sequence
	 * 
	 * @param st
	 *            a statement
	 */
	public void addStmnt(IStmnt ist) {
		steps.add(new Step(new Stmnt(ist)));
	}

	/**
	 * Add a variable declaration with specified type and name
	 * 
	 * @param type
	 * @param name
	 */
	public void addVariableDecl(String type, String name) {
		Step step = new Step();
		step.addVariableDecl(type, name);
		steps.add(step);
	}

	/**
	 * Add a randomization step.
	 */
	public void addRandomizationStep() {
		Step step = new Step();
		step.setRandomization(true);
		steps.add(step);
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (steps.size() == 0)
			throw new RuleViolationException(
					"A sequence must contain at least one step");
		String ret = steps.get(0).toCode();
		if (getSteps() != null) {
			int size = steps.size();
			for (int i = 1; i < size; i++)
				ret = ret + ";\n" + steps.get(i).toCode();
		}
		return ret;
	}
}
// /**
// * Points to the father proctype
// */
// Proctype fproctype;
// /**
// * Create an empty sequence with a father proctype.
// *
// * @param step
// */
// public Sequence(Proctype p) {
// super();
// setFproctype(p);
// }
// public Proctype getFproctype() {
// return fproctype;
// }
//
// public void setFproctype(Proctype fproctype) {
// this.fproctype = fproctype;
// }
