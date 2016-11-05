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

//import java.awt.Font;
import java.lang.String;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application {
	static GridPane grid = new GridPane();

	@Override
	public void start(Stage primaryStage) {
		try {			
			
			//grid.setGridLinesVisible(true);
			grid.setAlignment(Pos.BASELINE_LEFT);
			grid.setHgap(1);
			grid.setVgap(1);
			grid.setPadding(new Insets(1, 1, 1, 1));
			Scene scene = new Scene(grid, 500, 500);
			primaryStage.setScene(scene);
			
			
			Text welcome = new Text("Welcome to Critters!");
			welcome.setFont(Font.font("Arial",FontWeight.BOLD,14));
			grid.add(welcome, 0, 0,2,1);
			//GridPane.setConstraints(welcome,1,1);
			
			
			Label make = new Label("Add Critters");
			//GridPane.setConstraints(make,1,100);
			grid.add(make, 0, 10);
			
			//grid.getChildren().addAll(welcome,make);
			WorldDisplay worldStage = new WorldDisplay(); // this will only happen if the user chooses displayWorld... added later
			//primaryStage.show();
			worldStage.update();

			
			// Paints the icons.
			//Painter.paint();
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
        Critter.clearWorld(); // initialize the world map

		try {
			Critter.makeCritter("Craig");
			Critter.makeCritter("Critter1");
			Critter.makeCritter("Critter1");
			Critter.makeCritter("Algae");
		} catch(InvalidCritterException e) {
			System.out.println("you fucked up");
		}

		launch(args);
	}
}


