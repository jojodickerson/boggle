/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * Controller Class
 ***************************************************************/
package GameVersions.BoggleV2.p1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Controller {
	@FXML private ListView <String> usedWordsListView;
	@FXML private ListView <String> invalidWordsListView;
	@FXML private Pane mainPane;
	@FXML private Label totalPossibleWordsLabel;
	@FXML private Label gameMessengerLabel;
	@FXML private Label gamePointsLabel;

	@FXML private Button inputButton;
	@FXML private Button newGameButton;

	@FXML private TextField inputKey;
	private StringBuilder inputSB = new StringBuilder();

	private ObservableList<String> playedWords;
	private ObservableList<String> invalidWords;
	private GameManager gm;

	/**
	 * initialize()
	 * sets up new game and renders in scene
	 */
	 @FXML private void initialize(){
		 gm = new GameManager();

		 // gets gridBoard from GameManager and sets it in mainPane view
		 gm.gridBoard.setLayoutX(75);
		 gm.gridBoard.setLayoutY(75);
		 gm.gridBoard.minWidthProperty().setValue(275);
		 gm.gridBoard.minHeightProperty().setValue(275);
		 mainPane.getChildren().add(gm.gridBoard);
		//V4
//		for(Node d : gm.gridBoard.getChildren()){
//			d.setOnMouseClicked(hMouseEvent);
//		}
		gameMessengerLabel.setText("Game Messenger");

		playedWords = gm.getPlayedWords();
		invalidWords = FXCollections.observableArrayList ();
		usedWordsListView.setItems(playedWords);
		invalidWordsListView.setItems(invalidWords);

		 updateView();

		//Allows ENTER key to be used for user inputKey submit
		mainPane.setOnKeyReleased((event)->{
			if(event.getCode() == KeyCode.ENTER){
				checkInput();
			}
		});
	}

	/**
	 * updateView()
	 * helper method to bundle update of view objects
	 */
	private void updateView(){
		gamePointsLabel.setText(" Game Points: " + gm.getScore() + " ");
		inputKey.setText("");
		//display toggle for iff game has totalWordPossible turned on or not
		if(gm.gameWordCount() <= 0) {
			totalPossibleWordsLabel.setText("");
		}else {
			totalPossibleWordsLabel.setText(" Unique Words:" + gm.gameWordCount() + " ");
		}
		inputSB = new StringBuilder();
	}

	/**
	 * checkInput()
	 * takes inputKey from user and processes
	 */
	@FXML private void checkInput() {
		if (inputSB.toString().length() > 0) {
			gm.setCurrentWord(inputSB.toString().toUpperCase());
			inputSB = new StringBuilder();
		} else { gm.setCurrentWord(inputKey.getText().toUpperCase()); }

		String current = gm.getCurrentWord();

		if (gm.getCurrentWord().equals("")) {
			//do nothing and allow reset
		}else if (gm.alreadyPlayed()) {
			gameMessengerLabel.setText("\" "+current+" \" "+" is already played.");
			invalidWords.add(current);
		} else if (gm.isPlayable()) {
			gameMessengerLabel.setText("\" "+current+" \" "+" is a valid word.");
			gm.updateScore();
//			playedWords.add(current);
		}  else if (gm.isDictionaryWord()) {
			gameMessengerLabel.setText("\" "+current+" \" "+" is not a word on the current board.");
			invalidWords.add(current);
		} else {
			gameMessengerLabel.setText("\" "+current+" \" "+" is not a known word.");
			invalidWords.add(current);
		}

		gm.setCurrentWord("");
		updateView();
	}


	///////////////// EVENT HANDLERS /////////////////////////
	//TODO-V4 'clickable' tiles- build up sequence/ check for valid next tile
//	int move =1;
//	@FXML Label lastNode = null;
//	EventHandler<MouseEvent> hMouseEvent = new EventHandler<MouseEvent>() {
//		@Override
//		public void handle(MouseEvent event) {
//			if (lastNode == null) lastNode = (Die) event.getSource();
//			if (gm.gridBoard.getChildren().contains(event.getSource())) {
//				//TODO -need to check against used word list
//				Die currentNode = (Die) event.getSource();
//
//				if (currentNode.getBoundsInParent().intersects(lastNode.getBoundsInParent()) &&
//						!currentNode.getVisited()) {
//					currentNode.setVisited(true);
//					System.out.println(true);
//					inputSB.append(currentNode.getText());
//					System.out.println(inputSB.toString());
//					// add node to linkedHashList
//					System.out.println(lastNode);
//					System.out.println(currentNode);
//
//					lastNode = currentNode;
//
//				} else {
//					System.out.println(false);
//					System.out.println("Last node" + lastNode);
//				}
//
//			}
//		}
//	};

}
