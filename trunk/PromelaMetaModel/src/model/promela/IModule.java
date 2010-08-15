package model.promela;

public abstract class IModule implements ToCode {
	/**
	 * For ordering modules
	 */
	abstract protected int getPositionWeight();
}
