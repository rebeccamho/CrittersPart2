/* CRITTERS <Main.java>
 * EE422C Project 5 submission by
 * Irene Kuang
 * IK2684
 * 16480
 * Rebecca Ho
 * RH29645
 * 16480
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5;

import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	static GridPane grid = new GridPane();

	@Override
	public void start(Stage primaryStage) {
		try {			

			grid.setGridLinesVisible(true);

			Scene scene = new Scene(grid, 500, 500);
			primaryStage.setScene(scene);
			
			new WorldDisplay();
			primaryStage.show();
			
			// Paints the icons.
			Painter.paint();
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


