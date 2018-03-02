/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * Dictionary Class
 ***************************************************************/
package GameVersions.BoggleV2.p1;

import java.io.*;
import java.util.ArrayList;

public class Dictionary {
	//String Paths to files
	private final String animals = "/animals.txt";
	private final String dictionary = "/dictionary.txt";

	//p1 dictionary List populated by readFileIn()
	private final ArrayList<String> words = new ArrayList<>();
	private final int minWord, maxWord; //used to filter out words not withing length parameters

	/**
	 * Dictionary constructor
	 * @param min -min word size for game
	 * @param max -max word size for game/ board size (are the same)
	 */
	public Dictionary(int min, int max){
		this.minWord = min;
		this.maxWord = max;

		try {
			readFileIn(dictionary);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * getWords()
	 * @return - ArrayList that holds valid game dictionary words
	 */
	public ArrayList<String> getWords() {
		return words;
	}


	/**
	 * isInDictionary
	 * returns Bool for checked word
	 * --  intentionally set to Package-Private
	 * @param word -String to be checked against Dictionary
	 * @return - Boolean
	 */
	Boolean isInDictionary(String word){
		Boolean result = false;
		for(String w : words){
			if (w.equals(word)){ result = true; }
		}
		return result;
	}

	/**
	 * readFileIn
	 * reads text file into "words" Arraylist
	 * includes "length" filter for game min & max
	 * @param fileIn - .txt file to be read in
	 * @throws IOException -if no file found or unreadable
	 */
	private void readFileIn(String fileIn) throws IOException{
		try {
			InputStream inputStream = getClass().getResourceAsStream(fileIn);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String word;
			while ((word = br.readLine()) != null) {
				//filter condition for word length in game
				if(word.length() >= minWord && word.length() <= maxWord) {
					words.add(word.toUpperCase());
				}
			}
			br.close();
		}catch(IOException e){
			System.out.println("Cannot read file.");
			throw new RuntimeException(e);
		}
	}
}