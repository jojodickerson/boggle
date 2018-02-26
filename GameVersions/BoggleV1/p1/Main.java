/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * Main Application Class
 ***************************************************************/

package GameVersions.BoggleV1.p1;

import GameVersions.BoggleV2.p1.GameManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

//	    Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
//	    stage.setTitle("Let's Play Boggle!!! --JDickerson ");
//	    Scene scene = new Scene(root, 600, 500);
//	    scene.getStylesheets().add("assets/styles.css");
//	    scene.setFill(Color.BLUE);
//	    stage.setScene(scene);
//	    stage.show();
	    GameVersions.BoggleV2.p1.GameManager gm = new GameManager();
    }
    public static void main(String[] args) { launch(args); }
}
