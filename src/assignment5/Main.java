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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	static GridPane grid = new GridPane();

	@Override
	public void start(Stage primaryStage) {
		try {			
			
			//grid.setGridLinesVisible(true);
			grid.setAlignment(Pos.BASELINE_LEFT);
			grid.setHgap(5);
			grid.setVgap(2);
			grid.setPadding(new Insets(1, 1, 1, 1));
			Scene scene = new Scene(grid, 700, 700);
			primaryStage.setScene(scene);
			
			
			Text welcome = new Text("Welcome to Critters!");
			welcome.setFont(Font.font("Arial",FontWeight.BOLD,14));
			grid.add(welcome, 0, 0, 2, 1);
			
			/** make */
			Label make = new Label("Add Critters");
			grid.add(make, 0, 10);
			Label critterName = new Label("Critter Name:");
			grid.add(critterName, 0, 11);
			TextField nameField = new TextField();
			grid.add(nameField, 1, 11);
			Label critterNum = new Label("Number:");
			grid.add(critterNum, 2, 11);
			TextField numField = new TextField();
			grid.add(numField, 3, 11);
			numField.setMaxWidth(60);
			Button makeBtn = new Button();
	        makeBtn.setText("Make Critter(s)");
	        Text wrongInput = new Text("Invalid Number!");
			wrongInput.setFont(Font.font("Arial",FontWeight.NORMAL,12));
			wrongInput.setFill(Color.RED);
			
	        makeBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(grid.getChildren().contains(wrongInput)) {
	            		grid.getChildren().remove(wrongInput);
	            	}
	            	String name;
	            	String num;
	            	if((nameField.getText() != null && !nameField.getText().isEmpty())) {
	            		name = nameField.getText();
	            	}
	            	if((numField.getText() != null && !numField.getText().isEmpty())) {
	            		num = numField.getText();
	            		num = num.trim();
	            		// need a try block for invalid number
	            		try{
	            			int numInt = Integer.parseInt(num);
	            		} catch(NumberFormatException e) {
	            			grid.add(wrongInput, 3, 15);
	            		}
	            	}
	            }
	        });
	        grid.add(makeBtn, 7, 11);
			
			/** show */
	        Label show = new Label("Display World:");
			grid.add(show, 0, 30);
	        Button displayBtn = new Button();
	        displayBtn.setText("Show Critters");
	        
	        displayBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	new WorldDisplay();
	            }
	        });
	        grid.add(displayBtn, 1, 30);
			
			
			/** step */
			Label step = new Label("Perform Time Steps");
			grid.add(step, 0, 50);
			Label stepLabel = new Label("Number of Steps:");
			grid.add(stepLabel, 0, 51);
			TextField stepField = new TextField();
			grid.add(stepField, 1, 51);
			stepField.setMaxWidth(60);
			Button stepBtn = new Button();
	        stepBtn.setText("Perform Steps(s)");
	        Text wrongStepInput = new Text("Invalid Number!");
			wrongStepInput.setFont(Font.font("Arial",FontWeight.NORMAL,12));
			wrongStepInput.setFill(Color.RED);
			
	        stepBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(grid.getChildren().contains(wrongStepInput)) {
	            		grid.getChildren().remove(wrongStepInput);
	            	}
	            	String num;
	            	if((stepField.getText() != null && !stepField.getText().isEmpty())) {
	            		num = stepField.getText();
	            		num = num.trim();
	            		// need a try block for invalid number
	            		try{
	            			int numInt = Integer.parseInt(num);
	            		} catch(NumberFormatException e) {
	            			grid.add(wrongStepInput, 1, 52);
	            		}
	            	}
	            }
	        });
	        grid.add(stepBtn, 7, 51);
			
	        primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


