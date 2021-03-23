package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import model.HighScore;

/**
 * diese classe wird vir die verwaltung und speicherung der Highscore Objekte gebraucht
 
 * @author Adrian Nieto - EDU
 *
 */
public class HighScoreController {

	ArrayList<HighScore> scores;

	/**
	 * kleiner "Testrahmen"
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HighScoreController testController = new HighScoreController();
		testController.readHighscores();
		testController.sortScores();
		testController.printHighscores();
		
	}

	public void printHighscores() {
		for (HighScore hs : scores) {
			System.out.println(hs.getName() + ": " + hs.getScore());
		}
	}

	public ArrayList<HighScore> getHighscoreList() {
		return scores;
	}

	public void sortScores() {
		
		scores.sort((a,b) -> b.getScore()-a.getScore());
		
		scores.sort(new Comparator<HighScore>() {
			
			public int compare(HighScore a, HighScore b) {
				return b.getScore() - a.getScore();
			}
		});
	}

	/**
	 * liest alle HighScore-Objekte aus der Datei einlesen
	 */
	public void readHighscores() {
		scores = new ArrayList<>();
		BufferedReader bfr = null;
		File file = new File(System.getProperty("user.home") + "\\highscore.lst");
		try {
			file.createNewFile();
			FileReader reader = new FileReader(file);
			bfr = new BufferedReader(reader);
			while (bfr.ready()) { // liest solange er es kann gibt zeile aus
				String line = bfr.readLine();
				// System.out.println(line);
				scores.add(parseHighscore(line)); // HighScore-Objekt parsen und in Liste einfügen
			}

		} catch (IOException ioex) {
			System.out.println(file.getAbsolutePath());
			ioex.printStackTrace();
		} finally {
			try {
				bfr.close();
			} catch (IOException e) {

			}
		}
	}

	private HighScore parseHighscore(String line) {
		HighScore result = null;
		String[] parts = line.split(";");
		String name = parts[0];
		int punkte = Integer.parseInt(parts[1]);
		result = new HighScore(name, punkte);
		return result;
	}

	/**private void writeScores() {
		PrintWriter prnt = null;
		String[] names = {"Alpha", "Beta", "Gaga"} ;
		try {
			prnt = new PrintWriter(System.getProperty("user.home") + "\\highscore.lst");
			int i = 0;
			for (String value : names) {
				prnt.write(String.valueOf(i) + ";" + value + System.lineSeparator());
				i++;
			}
				
		}
		
		finally {
			try {
				prnt.flush();
				prnt.close();
			}
		}
		//auskommentiert da  immer wieder fehlermeldungen beim finally gekommen sind
		
	}*/
	
	
	public void writeScores() {
		PrintWriter prnt = null;
		try {
		prnt = new PrintWriter(System.getProperty("user.home") + "\\highscore.lst");
		// For-each loop zum abklappern der einzelnen HighScore objekte
		for (HighScore score : scores) {
		prnt.write(score.getName() + ";" + score.getScore() + System.lineSeparator());
		}
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}
		finally {
		prnt.flush(); // puffer leeren und ins file schreiben
		prnt.close(); // Systemresourcen freigeben
		}
		}

	public void addScore(HighScore af) {
		// TODO Auto-generated method stub
		scores.add(af);
	}
	
	
}
