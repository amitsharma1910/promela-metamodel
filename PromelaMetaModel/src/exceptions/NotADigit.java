package exceptions;

public class NotADigit extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6443384835185254673L;
	
	public NotADigit(){
		
	}
	public  NotADigit(String msg){
		super(msg);
	}
	@Override
	public String toString(){
		return "NotADigit";
	}
}
