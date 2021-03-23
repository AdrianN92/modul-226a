package controller;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import model.Food;
import model.HighScore;
import model.Robi;
import processing.core.PApplet;

/**
 * the stepstone is still heavy
 * @author Adrian Nieto - EDU
 *
 */
public class RobiGameController extends PApplet{

	Robi playerRobi;
	ArrayList<Food> foodItems;	
	ArrayList<Robi> kiEnemies;
	String playerName;
	String winner = "";
	Timer tim;
	TimerTask tTask;
	HighScoreController puntos = new HighScoreController();
	
	private static final int START=0, GAME=1, FINISH=2;
	private int gameState = START;
	
	/**
	 * erstellt das Spiel
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("controller.RobiGameController");
	}

	/**
	 * Definiert die Fenstergrösse
	 */
	public void settings() {
		size(800, 600);
	}
	
	

	/**
	 * konstruktoraufrufe, initialisiert die parameter
	 * 
	 */
	public void setup() {
		
		//erstellt 
		playerName = JOptionPane.showInputDialog("Namen eingeben");
		
		//erstellt spieler
		playerRobi = new Robi(100,100, 0xFFFF0000, this);
		
		//erstellt Gegner
		kiEnemies = new ArrayList<Robi>();
		Random r = new Random();
		for (int i=0; i < 2; i++) {
			Robi enemy = new Robi(r.nextInt(width),r.nextInt(height),0xFF0000FF,this);
			kiEnemies.add(enemy);			
		}
		
		//erstellt Food und speichert diese
		foodItems = new ArrayList<Food>();
		for (int i=0; i < 10; i++){
			foodItems.add(new Food(random(width), 
					random(height), 15, this));
		}
		// setup timer triggered game control handling
		tim = new Timer();
		tTask = new TimerTask() {
			@Override
			public void run() { // this method runs complete game logic 
				handleGameLogic();
			}			
		};
		//timer rift alle 100ms die run methode vom timertask objekt auf
		tim.scheduleAtFixedRate(tTask,new Date(), 100);
	}

	/**
	 *  Methode zum erstellen der objekte
	 */
	public void draw(){
		switch (gameState) {
		case START:
		case GAME: drawGame(); break;
		case FINISH: drawGameOver(); break;
		}
	}
	
	/**
	 * Methode zum erstellen des GameOver 
	 */
	private void drawGameOver() {
		
		fill(255);
		textSize(42);
		text("Game Over: " + winner, 100,100);
		float hoehe = 250;
		for (HighScore scores : puntos.getHighscoreList()) {
			
			text(scores.getName() + ": " + scores.getScore(), width/2, hoehe);
			hoehe += 50;
		}
		noLoop(); 
		
	}
	
	private void drawGame() {
		//definiert den Rand des Spieles
		background(0x202020);
		
		//fügt spieler ein
		playerRobi.draw();
		
		
		//fügt die erstellten Gegner ein
		for (int i=0; i < kiEnemies.size(); i++) {
			Robi enny = kiEnemies.get(i);
			enny.draw();
		}			
			
		//fügt die erstellten Food items an
		for (int i=0; i <foodItems.size(); i++){
			Food tFood = foodItems.get(i); 
			tFood.draw(); //draw i-th food object			
		}
		drawPlayerScores();
	}
	
	private void drawPlayerScores() {
		int fontSize = 20;
		int yPos = 40;
		textSize(20);
		fill(playerRobi.getColor());
		text("Player: "+playerRobi.getScore(), 10,yPos);
		for (int i = 0; i < kiEnemies.size(); i++) {
			Robi r = kiEnemies.get(i);
			fill(r.getColor());
			text("Ki "+i+": "+r.getScore(), width - 80, yPos + i*fontSize); 
		}		
	}
	
	/**
	 * diese methode startet die ganze Game Logik
	 */
	void handleGameLogic() {
		playerRobi.move();
		detectFoodCollisions(playerRobi);
		if (checkScore(playerRobi)) {
			winner = "You";
		}
		/**für jeden Gegner:
		 * finde nächstgelegenen food objekt
		 * bewege dich zum nächsten food objekt
		 */
		for (int i= 0; i < kiEnemies.size(); i++) {
			Robi ki = kiEnemies.get(i);
			Food nearestFood = null;
			double minimalDistance = Double.MAX_VALUE;
			
			for (Food f : foodItems) { //for Each konstrukt
				detectFoodCollisions(ki);
				double d = getDistance(ki, f);
				if (d <= minimalDistance) {
					minimalDistance = d;
					nearestFood = f;					
				}
			}
			
			if (Math.abs(ki.getxPos()-nearestFood.getxPos()) > Math.abs(ki.getyPos()-nearestFood.getyPos())){
				if (ki.getxPos()-nearestFood.getxPos() < 0) { //move rechts
					ki.moveRight();
				} else {
					ki.moveLeft();
				}
			} 
			else {
				if (ki.getyPos()-nearestFood.getyPos() < 0) { //move oben
					ki.moveDown();
				} else {
					ki.moveUp();
				}
			}
			ki.move();
			if (checkScore(ki)) {
				winner = "Ki-Robi "+(i);
			}
		}		
	}
	
	/**
	 * wenn Score des robis über 100 ist,
	 * set GameState GameOver, disable the timertask and return true
	 * @param r
	 * @return
	 */
	private boolean checkScore(Robi r) {
		if (r.getScore() >= 100) {
			gameState = FINISH;
			puntos.readHighscores();
			puntos.addScore(new HighScore(playerName, playerRobi.getScore()));
			puntos.sortScores();
			puntos.writeScores();
			tim.cancel();
			return true;
		}
		return false;
	}
	
	/**
	 * Erstellt neues Food Item, wenn Spieler/Gegner mit diesem "isst"
	 * und fügt diesen an einer neuen zuffäligen position hinzu
	 * zusätzlich wird Gegner/Spieler grösser wenn ein Food item gegessen wird
	 */
	void detectFoodCollisions(Robi v) {
		int catched = -1;
		for (int i=0; i < foodItems.size(); i++) {
			Food tFood = foodItems.get(i);
			if (checkCollision(v,tFood)){
				v.grow();
				foodItems.set(i, new Food(random(width), 
						random(height), 15, this));	
				v.addScore(tFood.getSize());
			} 	
		}		
	}
	
	/**
	 * Steuerung für spieler
	 */
	public void keyPressed(){
		switch (keyCode){
		case UP: playerRobi.moveUp(); break;
		case DOWN: playerRobi.moveDown(); break;
		case LEFT: playerRobi.moveLeft(); break;
		case RIGHT: playerRobi.moveRight(); break;
		
		}
		
	}

	/**
	 * Überprüft die Distanz zwischen Spieler und Food item
	 * wird für detectFoodColllisons gebraucht
	 */
	boolean checkCollision(Robi v, Food f){
		return getDistance(v,f) < (v.getRobiWidth()/2+f.getSize()/2);
	}
	
	float getDistance(Robi v, Food f) {
		float xDiff = v.getxPos() - f.getxPos();
		float yDiff = v.getyPos() - f.getyPos();
		return sqrt(xDiff*xDiff + yDiff*yDiff);
	}
}
