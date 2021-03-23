package model;
import processing.core.PApplet;

/**
 * 
 * @author Adrian Nieto - EDU
 *
 */
public class Robi {
	float xPos;
	float yPos;
	int color;
	int robiWidth = 20;
	int robiHeight = 20;
	PApplet myWindow;
	
	enum DIRECTION { NORTH, EAST, SOUTH,WEST };
	
	private DIRECTION direction = DIRECTION.EAST;
	private int score;
	
	/**
	 * Konstruktor zum initialiseren der Startwerte der Spieler
	 * @param f
	 * @param g
	 * @param color
	 * @param myWindow
	 */
	public Robi(float f, float g, int color, PApplet myWindow){
		this.xPos = f;
		this.yPos = g;
		this.color = color;
		this.myWindow = myWindow;
	}

	/**
	    Funktion zum Zeichnen eines "Viechs"
	 */
	public void draw(){
		myWindow.rectMode(PApplet.CENTER);
		myWindow.fill(this.color);
		myWindow.rect(this.getxPos(),this.getyPos(),getRobiWidth(), robiHeight);
		myWindow.fill(0xFFFFFFFF);
		myWindow.noStroke();
		myWindow.ellipse(getxPos()-getRobiWidth()/4f,getyPos(),robiHeight*0.7f,robiHeight*0.7f);
		myWindow.ellipse(getxPos()+getRobiWidth()/4f,getyPos(),robiHeight*0.7f,robiHeight*0.7f);
		myWindow.fill(0);
		myWindow.ellipse(getxPos()-getRobiWidth()/4f+1,getyPos()-2,robiHeight*0.4f,robiHeight*0.4f);
		myWindow.ellipse(getxPos()+getRobiWidth()/4f-1,getyPos()-2,robiHeight*0.4f,robiHeight*0.4f); 
	}
	
	/**
	 * Funktion für die Richtung der Spieler/Gegner
	 */
	public void move() {
		switch (direction) {
			case NORTH: yPos = Math.max(0, getyPos() - 5); break;
			case SOUTH: yPos = Math.min(myWindow.height, getyPos() + 5); break;
			case EAST: xPos = Math.min(myWindow.width,getxPos() + 5); break;
			case WEST : xPos = Math.max(0, getxPos() - 5); break;
		}
	}

	public void moveUp(){
		this.direction = DIRECTION.NORTH;
	}

	public void moveDown(){
		this.direction = DIRECTION.SOUTH;
	}

	public void moveLeft(){
		this.direction = DIRECTION.WEST;
	}

	public void moveRight(){
		this.direction = DIRECTION.EAST;
	}

	/**
	 * wird für das wachstum der Figur gebraucht
	 */
	public void grow(){
		robiWidth = getRobiWidth() + 2;
		robiHeight+=2;
	}

	/**
	 * Getters und Setters
	 * @return
	 */
	
	public int getRobiWidth() {
		return robiWidth;
	}

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void addScore(int size) {
		this.score += size;
	}
	
	public int getScore() {
		return score;
	}

	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}
}
