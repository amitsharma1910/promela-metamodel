package model.promela;

import exceptions.RuleViolationException;

public class Poll extends IAny_expr {
	private IPoll poll;

	public IPoll getPoll() {
		return poll;
	}

	public void setPoll(IPoll poll) {
		this.poll = poll;
	}

	public Poll(IPoll poll) {
		super();
		setPoll(poll);
	}

	public String toCode() throws RuleViolationException {
		if (poll == null)
			throw new RuleViolationException("poll must not be empty");
		return poll.toCode();
	}
}
