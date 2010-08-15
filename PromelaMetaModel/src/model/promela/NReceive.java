package model.promela;

import java.util.List;

import exceptions.RuleViolationException;

/**
 * normal receive
 * 
 * @author Duc
 * 
 */
public class NReceive extends IReceive {
	private Varref varref;
	private Recv_args rargs;

	public NReceive(Varref varref, Recv_args rargs) {
		super();
		setVarref(varref);
		setRargs(rargs);
	}

	public NReceive(String varref, List<String> recvVars) {
		super();
		setVarref(varref);
		Recv_args args = new Recv_args();
		for (int i = 0; i < recvVars.size(); i++) {
			args.addRecv_arg(recvVars.get(i));
		}
		setRargs(args);
	}

	public Varref getVarref() {
		return varref;
	}

	public void setVarref(Varref varref) {
		this.varref = varref;
	}

	public void setVarref(String varref) {
		this.varref = new Varref(varref);
	}

	public Recv_args getRargs() {
		return rargs;
	}

	public void setRargs(Recv_args rargs) {
		this.rargs = rargs;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (varref == null || rargs == null)
			throw new RuleViolationException("varref and recev_args must both exist in a normal receive");
		return getVarref().toCode() + "?" + getRargs().toCode();
	}
}
