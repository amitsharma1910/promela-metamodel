package model.promela.literal;

public class Alpha extends AlphNum{
	private Character alpha;
	public Alpha(){
		
	}
	public Alpha(Character alpha) throws Exception {
		super();
		setAlpha(alpha);
	}
	public Character getAlpha() {
		return alpha;
	}
	public void setAlpha(Character alpha) throws Exception{
		if(!Character.isLetter(alpha)) throw new Exception();
		this.alpha = alpha;
	}
	@Override
	public String toCode() {
		return getAlpha().toString();
	}
	
}
