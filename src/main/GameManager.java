/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * GameManager Class
 ***************************************************************/

package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

import java.util.*;

public class GameManager {

	private final int M = 5; //row width
	private final int N = 5; //column width
	private final int MIN_WORD = 3; // minimum word length
	private final int MAX_WORD = M * N; //max word length
	private int score = 0;
	private final Random rand = new Random();

	private Scanner sc = new Scanner(System.in); //v1 input
	private String currentWord; // current user input word

	private final String BOARD_STRING; //main string to populate charBoard and gridBoard
	private final char[][] charBoard = new char[M][N]; //used for isOnBoard search

	//FXML view grid ** intentionally set to Package-Private
	final GridPane gridBoard = new GridPane();

	private final Dictionary dict = new Dictionary(MIN_WORD, MAX_WORD);
	private final ArrayList<String> wordsOnBoard = new ArrayList<>();
	private final ObservableList<String> playedWords =
			FXCollections.observableArrayList();

	/**
	 * GameManager()
	 * Default Constructor
	 */
	public GameManager() {
		BOARD_STRING = buildBoardString();
		populateGrid();
		populateCharBoard();
//		findAllWords(); //V3
//		System.out.println("Board String: \n" + BOARD_STRING);
//		System.out.println("BoardWordList:");
//		System.out.println(playedWords);
//		System.out.println("Unique Words: " + wordsOnBoard.size());
	}

	//////// Getters & Setters --  intentionally set to Package-Private   ////////////
	void setCurrentWord(String currentWord) { this.currentWord = currentWord; }
	String getCurrentWord() { return currentWord; }
	int gameWordCount(){ return wordsOnBoard.size() - playedWords.size(); }
	int getScore() { return score; }
	void updateScore(){ score += currentWord.length() - 2; }
	ObservableList<String> getPlayedWords() { return playedWords; }

	/**
	 * Boolean isPlayable
	 * --  intentionally set to Package-Private
	 * @return - Boolean - true if on board, is a word, and not yet played
	 */
	Boolean isPlayable() {
		Boolean result = !alreadyPlayed() && isOnBoard() && isDictionaryWord();
		if (result) playedWords.add(currentWord);
		//System.out.println(playedWords);
		return result;
	}

	/**
	 * Boolean alreadyPlayed()
	 * @return - checks list of valid words already played - false if not played
	 */
	protected Boolean alreadyPlayed(){
		Boolean result = false;
		for (String w : playedWords) {
			if (w.equals(currentWord)) {
				result = true;
			}
		}
		return result;
	}
	/**
	 * Boolean isDictionaryWord
	 * helper method to check if word is in dictionary
	 * @return -Boolean
	 */
	protected Boolean isDictionaryWord(){
		return  dict.isInDictionary(currentWord);
	}

	/**
	 * Boolean isOnBoard()
	 * @return - true if current word is on board
	 */
	private Boolean isOnBoard() {
		Boolean onBoard = false;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				populateCharBoard(); //create fresh copy of board
				onBoard = gridSearch(i,j,0);
				if(onBoard){
					i=M; j = N;/*end searching when found */
					//System.out.println("Found...");
				}
			}
		}
		//System.out.println("isOnBoard:" + onBoard);
		return onBoard;
	}

	/**
	 * Boolean gridSearch
	 * - helper method for isOnBoard() that searches for word / path match
	 *
	 * @param i - current char[M][]
	 * @param j - current char[][N]
	 * @param k - currentWord char index (starts at 0) then increments via recursion
	 * @return - returns true if word path found on board
	 */
	private Boolean gridSearch(int i, int j, int k) {
		if (i < 0 || j < 0 || i >= M || j >= N || k > currentWord.length() - 1) {
			return false;
		} else if (charBoard[i][j] == (currentWord.charAt(k))) {
			//System.out.println("match " + charBoard[i][j]);

			//replaces matched char with lower case to avoid path re-use
			charBoard[i][j] = currentWord.toLowerCase().charAt(k);
//			printBoard(charBoard); //for dev humans

			if (k == currentWord.length() - 1) {
				//System.out.println("Last letter found. " + currentWord.charAt(k));
				return true;
			} else {
				//System.out.println("Checking other paths...");
				return (gridSearch(i - 1, j, k + 1)
						|| gridSearch(i + 1, j, k + 1)
						|| gridSearch(i, j - 1, k + 1)
						|| gridSearch(i, j + 1, k + 1)
						|| gridSearch(i + 1, j + 1, k + 1)
						|| gridSearch(i - 1, j - 1, k + 1)
						|| gridSearch(i - 1, j + 1, k + 1)
						|| gridSearch(i + 1, j - 1, k + 1));
			}
		} else {
			return false;
		}
	}

	/**
	 * findAllWords()
	 * dev helper function that creates a full list of words on the board
	 */
	private void findAllWords() {
		for (String w : dict.getWords()) {
			currentWord = w;
			if (isOnBoard()) {
				//add to list of wordsOnBoard
				wordsOnBoard.add(currentWord);
			}
		}
		System.out.println(wordsOnBoard);
		currentWord = null;
	}

	/**
	 * printBoard
	 * dev helper function
	 *
	 * @param board - prints this char[][] on console for dev humans
	 */
	private void printBoard(char[][] board) {
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				char c = board[i][j];
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	/**
	 * buildBoardString()
	 *
	 * @return - String - generated BOARD_STRING assign to var BOARD_STRING
	 */
	private String buildBoardString() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		while (sb.length() < MAX_WORD) {
			char ch = str.charAt(rand.nextInt(str.length()));
			if (checkCharFrequency(ch, sb)) { sb.append(ch); }
		}
		return checkForQ(sb);
	}

	// helper for buildBoardString
	private Boolean checkCharFrequency(char ch, StringBuilder sb){
		//Count char frequency
		int frequency = 0;
		for (int i = 0; i < sb.length(); i++) {
			if (ch == sb.charAt(i)) {
				++frequency;
			}
		}

		return (frequency < 4);
	}

	// helper for buildBoardString
	private String checkForQ(StringBuilder sb) {
		for (int i = 0; i < MAX_WORD; i++) {
			int randNum = rand.nextInt(100);
			int ceiling = 30; //increase U by Q approx 30%

			if (sb.charAt(i) == 'Q' && randNum <= ceiling) {
				//Make a list of current'Q' adjacent spots
				ArrayList<Integer> adjList = new ArrayList<>();
				adjList.addAll(Arrays.asList(i - M, i + M));
				if (i % M != 0) {adjList.addAll(Arrays.asList(i - 1, i - M - 1, i + M - 1));}
				if (i % M != M - 1) {adjList.addAll(Arrays.asList(i + 1, i + M + 1, i - M + 1));}

				//Filter any out-of-bounds adjacent spots
				adjList.removeIf((Integer in) -> in < 0 || in >= MAX_WORD);

				//checks if any valid adjacent spots are already 'U'
				Boolean adjU = false;
				for (Integer in : adjList) {
					if (sb.charAt(in) == 'U') { adjU = true; }
				}

				//adds 'U' in adjacent spot iff 'U' frequency < 4 and no existing adj 'U'
				if (!adjU && checkCharFrequency('U', sb)) {
					System.out.println(sb.toString());
					int adjacentSpot = adjList.get(rand.nextInt(adjList.size()));
					sb.setCharAt(adjacentSpot,'U');
					System.out.println(sb.toString() + "\n U placed at index " + adjacentSpot);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * void populateGrid()
	 * populates GridPane gridBoard with Die Labels and assigns letters from BOARD_STRING
	 */
	private void populateGrid() {
		int k = 0;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				String d = BOARD_STRING.substring(k, k + 1);
				Die die = new Die(d);
				gridBoard.add(die, j, i);
				k++;
			}
		}
	}

	/**
	 * void populateCharBoard()
	 * populates char[][] charBoard with BOARD_STRING for isOnBoard word search
	 */
	private void populateCharBoard() {
		char[] c = BOARD_STRING.toCharArray();
		int k = 0;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				charBoard[i][j] = c[k];
				k++;
			}
		}
	}

	/* branch test1 */
	//V1
//	private void compareWordsFromCMDLine() {
//		String currentWord;
//		System.out.println("Enter a word: ");
//
//		while (sc.hasNextLine()) {
//			currentWord = sc.nextLine();
//			if(dict.isInDictionary(currentWord)){
//				System.out.println("\" " +currentWord +" \" " + " is a valid word.");
//			}else{
//				System.out.println("\" " +currentWord +" \" " + " is NOT a valid word.");
//			}
//		}
//	}
}
