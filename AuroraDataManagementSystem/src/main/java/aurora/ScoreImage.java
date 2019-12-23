package aurora;

public class ScoreImage {

	int score;
	String name;

	ScoreImage()
	{
		score = 0;
		name = "";
	}

	public ScoreImage(int score, String name)
	{
		this.score=score;
		this.name=name;
	}

	public int getScore()
	{
		return score;
	}

	public String getName()
	{
		return name;
	}

	public void setScore(int score)
	{
		this.score=score;
	}

	public void setName(String name)
	{
		this.name=name;
	}

}
