package aurora;

import java.util.List;

public class trainset {
	private float[][] sift;
	private int type;

	public trainset(float[][] sift , int type)
	{
		setSift(sift);
		setType(type);
		
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public void setSift(float[][] sift)
	{
		this.sift = sift;
	}
	
	
	public int getType()
	{
		return type;
	}
	
	public float[][] getSift()
	{
		return sift;
	}
}
