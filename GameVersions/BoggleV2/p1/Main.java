/***************************************************************
 * UNM CS 351L Spring '18
 * Boggle Game V2
 * Joanna Dickerson
 *
 * Main Application Class
 ***************************************************************/

package GameVersions.BoggleV2.p1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

	    Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
	    stage.setTitle("Let's Play Boggle!!! --JDickerson ");
	    Scene scene = new Scene(root, 600, 500);
	    scene.getStylesheets().add("/styles.css");
	    stage.setScene(scene);
	    stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
