package model.promela;

import java.util.List;

public abstract class ISend implements ToCode {
	/**
	 * Get list of variables in the sent argument.
	 * 
	 * @return
	 */
	public abstract List<Varref> getVarrefs();
}
