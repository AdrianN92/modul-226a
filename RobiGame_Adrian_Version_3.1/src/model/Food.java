package model;
import processing.core.PApplet;

/**
 * Javadoc  Part deux
 * @author Adrian Nieto - EDU
 * (fork von Sven)
 *
 */

public class Food {
	float xPos;
	float yPos;
	int myColor = 0xFFFFAA00; 
	int size;
	PApplet myWindow;
	
	/**
	 * Konstruktor zum initialiseren der Startwerte der bei Food
	 * @param xPos
	 * @param yPos
	 * @param size
	 * @param myWindow
	 */
	
	public Food(float xPos, float yPos, int size, PApplet myWindow){
		this.xPos = xPos;
		this.yPos = yPos;
		this.size = size;
		this.myWindow = myWindow; 
	}
	
	/**
	 * Funktion zum Zeichnen eines Foods
	 */
	public void draw(){
		myWindow.fill(myColor);
		myWindow.ellipse(getxPos(),getyPos(),getSize(),getSize());
		myWindow.fill(0xFF55AA00);
		myWindow.ellipse(getxPos(),getyPos(),getSize()/2,getSize()/2);    
	}
	
	/**
	 * Setters und Getters
	 * @return
	 */
	public int getSize() {
		return size;
	}
	public float getxPos() {
		return xPos;
	}
	public float getyPos() {
		return yPos;
	}
}

