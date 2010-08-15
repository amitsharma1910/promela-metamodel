package model.promela;

import java.util.ArrayList;
import java.util.List;

import model.promela.literal.Literal;
import exceptions.RuleViolationException;

public class Ch_init implements ToCode {
	/**
	 * Number of message in a buffer.
	 */
	private Const cnst;
	/**
	 * Types of messages transferred over the channel.
	 */
	private List<Typename> typenames = new ArrayList<Typename>();

	public Ch_init(Const cnst, Typename typename) throws Exception {
		super();
		setCnst(cnst);
		addTypename(typename);
	}

	/**
	 * Initialize a rendezvous channel with a list of type names.
	 * 
	 * @param typenames
	 */
	public Ch_init(List<String> typenames) {
		super();
		setCnst("0");
		for (int i = 0; i < typenames.size(); i++) {
			this.typenames.add(new Typename(typenames.get(i)));
		}
	}

	/**
	 * Initialize a buffered channel with a list of type names.
	 * 
	 * @param bufferCap
	 *            Buffer capacity.
	 * @param typenames
	 *            List of type names.
	 */
	public Ch_init(String bufferCap, List<String> typenames) {
		super();
		setCnst(bufferCap);
		if (typenames != null)
			for (int i = 0; i < typenames.size(); i++) {
				this.typenames.add(new Typename(typenames.get(i)));
			}
	}

	/**
	 * Add one message type into the list of message types; if it does not exist
	 * 
	 * @param mesType
	 */
	public void addMessageType(String mesType) {
		if (!containsMesType(mesType))
			typenames.add(new Typename(mesType));
	}

	/**
	 * 
	 * @param mesType
	 * @return
	 */
	public boolean containsMesType(String mesType) {
		for (int i = 0; i < typenames.size(); i++) {
			Typename tn = typenames.get(i);
			try {
				if (tn.getTypename().toCode().equals(mesType))
					return true;
			} catch (RuleViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Add one typename into the list
	 * 
	 * @param tn
	 */
	public void addTypename(Typename tn) {
		typenames.add(tn);
	}

	/**
	 * Get a specific typename.
	 * 
	 * @param typename
	 * @return null if not found.
	 */
	public Typename getTypename(String typename) {
		for (Typename type : typenames) {
			try {
				if (type.getTypename().toCode().equals(typename))
					return type;
			} catch (RuleViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public Const getCnst() {
		return cnst;
	}

	public void setCnst(String cnst) {
		this.cnst = new Const(cnst);
	}

	public void setCnst(Const cnst) {
		this.cnst = cnst;
	}

	/**
	 * Get message type name in this channel
	 * 
	 * @return
	 */
	public List<Typename> getTypenames() {
		return typenames;
	}

	public void setTypenames(List<Typename> typenames) {
		this.typenames = typenames;
	}

	@Override
	public String toCode() throws RuleViolationException {
		if (cnst == null || typenames.size() == 0)
			throw new RuleViolationException(
					"There must be at least one const and one typename in a channel initializer");
		String ret = "[" + getCnst().toCode() + "] " + Literal.OF + " {" + getTypenames().get(0).toCode();
		int size = getTypenames().size();
		for (int i = 1; i < size; i++)
			ret = ret + ", " + getTypenames().get(i).toCode();
		return ret + "}";
	}
}
