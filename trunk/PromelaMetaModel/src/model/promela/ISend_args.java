package model.promela;

import java.util.List;

public abstract class ISend_args implements ToCode {
	/**
	 * Add a variable reference to this argument list.
	 * 
	 * @param varName
	 */
	public abstract void addVar(String varName);

	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	public abstract List<Varref> getVarrefs();
}
