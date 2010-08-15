package model.promela;

public abstract class IRecv_args implements ToCode {
	/**
	 * Add one receive argument into the receive-arguments
	 * 
	 * @param arg
	 */
	public abstract void addRecvArg(String arg);
}
