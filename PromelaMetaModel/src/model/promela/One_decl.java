package model.promela;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

import model.promela.literal.Literal;
import model.promela.literal.Visible;

public class One_decl implements ToCode {
	private Visible visible = null;
	private Typename typename = null;
	private List<Ivar> ivars = new ArrayList<Ivar>();

	public One_decl() {
	}

	public One_decl(Visible visible, Typename typename, List<Ivar> ivars) throws Exception {
		super();
		setVisible(visible);
		setTypename(typename);
		setIvars(ivars);
	}

	public One_decl(Typename typename, List<Ivar> ivars) throws Exception {
		super();
		setVisible(null);
		setTypename(typename);
		setIvars(ivars);
	}

	/**
	 * Create a declaration of a variable with specified type and name.
	 * 
	 * @param typename
	 * @param varName
	 */
	public One_decl(String typename, String varName) {
		super();
		setVisible(null);
		setTypename(new Typename(typename));
		addIvar(new Ivar(varName));
	}

	/**
	 * Create a declaration of a numeric variable with specified type, name and
	 * initialized constant.
	 * 
	 * @param typename
	 *            a Constant from Literal.
	 * @param varName
	 * @param initcnst
	 *            an initial value.
	 */
	public One_decl(String typename, String varName, String initCnst) {
		super();
		setTypename(typename);
		addIvar(new Ivar(varName, initCnst));
	}

	/**
	 * Create a declaration of a channel.
	 * 
	 * @param chanName
	 *            channel name.
	 * @param bufferCap
	 *            channel capacity.
	 * @param messageTypes
	 *            list of message type that can be transferred.
	 */
	public One_decl(String chanName, int bufferCap, List<String> messageTypes) {
		setTypename(Literal.CHAN);
		addIvar(new Ivar(chanName, bufferCap, messageTypes));
	}

	/**
	 * Add one message type into the list of message types
	 * 
	 * @param mesType
	 *            a message type
	 * @param chanName
	 *            a channel name
	 */
	public void addMessageTypetoChannel(String chanName, String mesType) {
		// find the channel name in the ivar list
		for (int i = 0; i < ivars.size(); i++) {
			Ivar ivar = ivars.get(i);
			// ADD a message type into the ivar list
			if (ivar.getName().getName().equals(chanName))
				ivar.addMessageType(mesType);
		}
	}

	/**
	 * Add one ivar into ivar list
	 * 
	 * @param ivar
	 */
	public void addIvar(Ivar ivar) {
		ivars.add(ivar);
	}

	/**
	 * Checks if the variable name is in ivar list.
	 * 
	 * @param varName
	 * @return
	 */
	public boolean containVarName(String varName) {
		for (int i = 0; i < ivars.size(); i++) {
			if (ivars.get(i).getName().getName().equals(varName))
				return true;
		}
		return false;
	}

	public Visible getVisible() {
		return visible;
	}

	public void setVisible(Visible visible) {
		this.visible = visible;
	}

	public Typename getTypename() {
		return typename;
	}

	public void setTypename(Typename typename) {
		this.typename = typename;
	}

	public void setTypename(String typename) {
		this.typename = new Typename(typename);
	}

	public List<Ivar> getIvars() {
		return ivars;
	}

	public void setIvars(List<Ivar> ivars) {
		this.ivars = ivars;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (typename == null || ivars.size() == 0)
			throw new RuleViolationException("typename and ivar must not be empty");
		String ret = "";
		if (visible != null)
			ret = ret + visible + " ";
		ret = ret + getTypename().toCode() + " " + ivars.get(0).toCode();
		for (int i = 1; i < ivars.size(); i++)
			ret = ret + "," + getIvars().get(i).toCode();
		return ret;
	}
}
