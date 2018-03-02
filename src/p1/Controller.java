/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V4
 * Joanna Dickerson
 *
 * Controller Class
 ***************************************************************/
package p1;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Controller {
	@FXML private ListView <String> usedWordsListView;
	@FXML private ListView <String> invalidWordsListView;
	@FXML private Pane mainPane;
	@FXML private Label totalPossibleWordsLabel;
	@FXML private Label gameMessengerLabel;
	@FXML private Label gamePointsLabel;
	@FXML private Label inputSBLabel;

	@FXML private Button inputButton;
	@FXML private Button newGameButton;
	@FXML private Button clearButton;

//	@FXML private TextField inputKey; //discarded V4
	private StringBuilder inputSB = new StringBuilder();

	private ObservableList<String> invalidWords;
	private GameManager gm;

	private final Integer GAME_TIME = 180;
	private Timeline timeline;
	@FXML private Label timerLabel = new Label();
	private final IntegerProperty timeSeconds = new SimpleIntegerProperty(GAME_TIME);

	/**
	 * initialize()
	 * sets up new game and renders in scene
	 */
	@FXML private void initialize() {
		startGameManager();
		//set scene view objects
		gameMessengerLabel.setText("Game Messenger");
		invalidWords = FXCollections.observableArrayList();
		usedWordsListView.setItems(gm.getPlayedWords());
		invalidWordsListView.setItems(invalidWords);
		newGameButton.setText("New Game");

		//set control object actions
		clearButton.setOnAction(event -> resetBoardPlay());
		inputButton.setOnAction(event -> checkInput());
		newGameButton.setOnAction(event -> startTimeLine());
		mainPane.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
				checkInput();
			}
		});
		startTimeLine();
	}

	/**
	 * startGameManager
	 * helper for initialize() to contain GameManager config
	 */
	private void startGameManager(){
		gm = new GameManager();
		gm.gridBoard.setLayoutX(75);
		gm.gridBoard.setLayoutY(75);
		gm.gridBoard.minWidthProperty().setValue(275);
		gm.gridBoard.minHeightProperty().setValue(275);
		mainPane.getChildren().add(gm.gridBoard);
		//set Nodes to be click-able
		for(Node d : gm.gridBoard.getChildren()){
			d.setOnMouseClicked(hMouseEvent);
		}
		updateView();
	}

	/**
	 * startTimeLine()
	 * helper for initialize() to contain timeline config
	 */
	private void startTimeLine() {
		//reset timeline if
		if (timeline != null) {
			timeline.stop();
		}
		timeline = new Timeline();
		timeSeconds.set(GAME_TIME);

		timerLabel.textProperty().bind(timeSeconds.asString());
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(GAME_TIME),
				new KeyValue(timeSeconds, 0)));

		timeline.playFromStart();

		//End of Game
		timeline.setOnFinished(done -> {
			gameMessengerLabel.setText("Time's UP!! You scored " + gm.getScore() + " points!");
			resetBoardPlay();
			//stop nodes from being click-able at end-of-game
			for (Node d : gm.gridBoard.getChildren()) {
				d.setOnMouseClicked((event) -> { /* do nothing */ });
			}
		});
	}

	/**
	 * updateView()
	 * helper method to bundle update of view objects
	 */
	private void updateView() {
		gamePointsLabel.setText(" Game Points: " + gm.getScore() + " ");

		//display toggle for iff game has totalWordPossible turned on or not
		if (gm.gameWordCount() <= 0) {
			totalPossibleWordsLabel.setText("");
		} else {
			totalPossibleWordsLabel.setText(" Unique Words:" + gm.gameWordCount() + " ");
		}
		resetBoardPlay(); //V4
	}

	/*
	* resetBoardPlay()
	* resets Die Nodes and inputSB for V4 all-GUI play
	* */
	@FXML private void resetBoardPlay() {
		lastNode = null; //V4
		inputSB = new StringBuilder();
		inputSBLabel.setText("");
		for (Node d : gm.gridBoard.getChildren()) {
			Die die = (Die) d;
			die.setVisited(false);
			die.getStyleClass().remove("dieVisited");
		}
	}

	/**
	 * checkInput()
	 * takes inputKey from user and processes
	 */
	@FXML private void checkInput() {
		gm.setCurrentWord(inputSB.toString().toUpperCase());
		inputSB = new StringBuilder();

		String current = gm.getCurrentWord();

		if (gm.getCurrentWord().equals("")) {
			//do nothing and allow reset
		} else if (gm.alreadyPlayed()) {
			gameMessengerLabel.setText("\" " + current + " \" " + " is already played.");
			invalidWords.add(current);
		} else if (gm.isPlayable()) {
			gameMessengerLabel.setText("\" " + current + " \" " + " is a valid word.");
			gm.updateScore();
		} else if (gm.isDictionaryWord()) {
			gameMessengerLabel.setText("\" " + current + " \" " + " is not a word on the current board.");
			invalidWords.add(current);
		} else {
			gameMessengerLabel.setText("\" " + current + " \" " + " is not a known word.");
			invalidWords.add(current);
		}
		resetBoardPlay(); //V4
		updateView();
	}


	/**
	 *  hMouseEvent
	 *  handles input on boardGrid and builds up word string relative to last selected node
	 */
	private Label lastNode = null; // bookeeping copy of last node played to build up word string
	private EventHandler<MouseEvent> hMouseEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (lastNode == null) lastNode = (Die) event.getSource();
			if (gm.gridBoard.getChildren().contains(event.getSource())) {
				Die currentNode = (Die) event.getSource();

				if (currentNode.getBoundsInParent().intersects(lastNode.getBoundsInParent()) &&
						!currentNode.getVisited()) {
					currentNode.setVisited(true);
					inputSB.append(currentNode.getText());
					inputSBLabel.setText(inputSB.toString());
					currentNode.getStyleClass().add("dieVisited");

					lastNode = currentNode;
				}
			}
		}
	};

}
