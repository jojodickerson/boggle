/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * Dictionary Class
 ***************************************************************/
package GameVersions.BoggleV1.p1;

import java.io.*;
import java.util.ArrayList;

public class Dictionary {
	//String Paths to files
	private String animals = "/animals.txt";
	private String dictionary = "/dictionary.txt";

	//p1 dictionary List populated by readFileIn()
	private ArrayList<String> words = new ArrayList<>();

	/**
	 * Dictionary constructor
	 */
	public Dictionary(){

		try {
			readFileIn(dictionary);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}


	/**
	 * isInDictionary
	 * returns Bool for checked word
	 * @param word -String to be checked against Dictionary
	 * @return - Boolean
	 */
	public Boolean isInDictionary(String word){
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
					words.add(word.toUpperCase());
			}
			br.close();
		}catch(IOException e){
			System.out.println("Cannot read file.");
			throw new RuntimeException(e);
		}
	}
}