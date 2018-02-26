/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * GameManager Class
 ***************************************************************/

package GameVersions.BoggleV1.p1;

import GameVersions.BoggleV2.p1.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

import java.util.*;

public class GameManager {
	GameVersions.BoggleV2.p1.Dictionary dict = new Dictionary();
	private Scanner sc = new Scanner(System.in);

	public GameManager(){

		compareWordsFromCMDLine();
	}

	//V1
	private void compareWordsFromCMDLine() {
		String currentWord;
		System.out.println("Enter a word: ");

		while (sc.hasNextLine()) {
			currentWord = sc.nextLine().toUpperCase();
			if(dict.isInDictionary(currentWord)){
				System.out.println("\" " +currentWord +" \" " + " is a valid word.");
			}else{
				System.out.println("\" " +currentWord +" \" " + " is NOT a valid word.");
			}
		}
	}
}
