package model;

public class HighScore {
	private String name;
	private int score;
	/**
	 * @param name
	 * @param score
	 */
	public HighScore(String name, int score) {
		super();
		this.name = name;
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	
}

