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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;
import assignment5.Critter;
import assignment5.InvalidCritterException;



public class Main extends Application {
    static ByteArrayOutputStream testOutputString;	
    static PrintStream old = System.out;	// if you want to restore output to console
	
	static GridPane grid = new GridPane();
	WorldDisplay worldStage; 
	boolean worldStageInit = false; 
	StatsDisplay statsStage;
	boolean statsStageInit = false;
	private double speed;
	Text statsOutput = new Text();
	public static ArrayList<String> statsCritters = new ArrayList<String>();

	@Override
	public void start(Stage primaryStage) {
		try {			
			
			//grid.setGridLinesVisible(true);
			grid.setAlignment(Pos.BASELINE_LEFT);
			grid.setHgap(5);
			grid.setVgap(2);
			grid.setPadding(new Insets(10, 10, 10, 10));
			Scene scene = new Scene(grid, 550, 550);
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
			nameField.setMaxWidth(100);
			grid.add(nameField, 1, 11);
			Label critterNum = new Label("Number:");
			grid.add(critterNum, 0, 12);
			TextField numField = new TextField();
			grid.add(numField, 1, 12);
			numField.setMaxWidth(60);
			Button makeBtn = new Button();
	        makeBtn.setText("Make Critter(s)");
	        Text wrongInput = new Text("Invalid Number!");
			wrongInput.setFont(Font.font("Arial",FontWeight.NORMAL,12));
			wrongInput.setFill(Color.RED);
			Text wrongCritterType = new Text("Invalid Critter Type!");
			wrongCritterType.setFont(Font.font("Arial",FontWeight.NORMAL,12));
			wrongCritterType.setFill(Color.RED);
			
	        makeBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(grid.getChildren().contains(wrongInput)) { // remove wrong number text
	            		grid.getChildren().remove(wrongInput);
	            	}
	            	if(grid.getChildren().contains(wrongCritterType)) { // remove wrong critter text
	            		grid.getChildren().remove(wrongCritterType);
	            	}
	            	String name = null;
	            	String num = null;
	            	int numInt = 0;
	            	if((nameField.getText() != null && !nameField.getText().isEmpty())) { // get Critter name
	            		name = nameField.getText();
	            		
	            	}
	            	if((numField.getText() != null && !numField.getText().isEmpty())) {
	            		num = numField.getText();
	            		num = num.trim();
	            		try{
	            			numInt = Integer.parseInt(num);
	            		} catch(NumberFormatException e) {
	            			grid.add(wrongInput, 1, 14);
	            		}
	            	}
	            	try {
	            		if(num == null) { // no number was specified
	            			numInt = 1;
	            		}
	            		String myPackage = Critter.class.getPackage().toString().split(" ")[1];
         				List<Critter> critters = Critter.getInstances(name);
         				String critter_class_name = myPackage + "." + name;
         			         				
         				Critter testCritter; 
         				Class<?> testClass; 
         				try { // this try block is to determine whether the critter is valid or not, regardless of number input
             				testCritter = (Critter) Class.forName(critter_class_name).newInstance();
             				testClass = testCritter.getClass();
         				} catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
         					throw new InvalidCritterException(critter_class_name);
         				} 
         	
	            		for(int i = 0; i < numInt; i++) { // add Critters to population and world
	            			Critter.makeCritter(name);
	            			if(!worldStageInit) {
	            				worldStage = new WorldDisplay();
	            				worldStageInit = true; 
	            			} else {
	            				worldStage.update();
	            			}
	            			if(!statsCritters.contains(name)) { // add Critter to stats list
	            				statsCritters.add(name);
	            			}
	            			if(!statsStageInit) {
	            				statsStage = new StatsDisplay();
	            				statsStageInit = true; 
	            				statsStage.update(statsCritters);
	            			} else {
	            				statsStage.update(statsCritters);
	            			}
	            		}
	            	} catch(InvalidCritterException e) { 
	            		grid.add(wrongCritterType,0,14);
	            	}
	            }
	        });
	        grid.add(makeBtn, 1, 13);
			
			/** show */
	        Label show = new Label("Display World:");
			grid.add(show, 10, 10);
	        Button displayBtn = new Button();
	        displayBtn.setText("Show Critters");
	        
	        displayBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(!worldStageInit) {
	            		worldStage = new WorldDisplay();	
	            		worldStageInit = true;
	            	} else {
	            		worldStage.update();
	            	}
	            	if(!statsStageInit) {
        				statsStage = new StatsDisplay();
        				statsStageInit = true; 
        				statsStage.update(statsCritters);
        			} else {
        				statsStage.update(statsCritters);
        			}
	            	
	            }
	        });
	        grid.add(displayBtn, 10, 11);
			
			
			/** step */
			Label step = new Label("Perform Time Steps");
			grid.add(step, 0, 30);
			Label stepLabel = new Label("Number of Steps:");
			grid.add(stepLabel, 0, 31);
			TextField stepField = new TextField();
			grid.add(stepField, 1, 31);
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
	            	String num = null;
	            	int numInt = 0;
	            	if((stepField.getText() != null && !stepField.getText().isEmpty())) {
	            		num = stepField.getText();
	            		num = num.trim();
	            		try{
	            			numInt = Integer.parseInt(num);
	            		} catch(NumberFormatException e) {
	            			grid.add(wrongStepInput, 1, 33);
	            		}
	            	}
	            	if(num == null) {
	            		numInt = 1;
	            	}
	            	for(int i = 0; i < numInt; i++) {
	            		Critter.worldTimeStep();
		            	if(!worldStageInit) {
		            		worldStage = new WorldDisplay();	
		            		worldStageInit = true;
		            	} else {
		            		worldStage.update();
		            	}
		            	if(!statsStageInit) {
            				statsStage = new StatsDisplay();
            				statsStageInit = true; 
            				statsStage.update(statsCritters);
            			} else {
            				statsStage.update(statsCritters);
            			}
	            	}
	            }
	        });
	        grid.add(stepBtn, 1, 32);
			
	        /** stats */
	        Label stats = new Label("Show Statistics:");
			grid.add(stats, 10, 30);
			/*
			Label statsCritterName = new Label("Critter Name:");
			grid.add(statsCritterName, 10, 31);
			TextField statsNameField = new TextField();
			statsNameField.setMaxWidth(100);
			grid.add(statsNameField, 11, 31);
			*/
	        Button statsBtn = new Button();
	        statsBtn.setText("Run Stats");
	        /*
	        Text wrongStatsInput = new Text("Invalid Critter Type!");
			wrongStatsInput.setFont(Font.font("Arial",FontWeight.NORMAL,12));
			wrongStatsInput.setFill(Color.RED);
			*/
	        statsBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(!statsStageInit) {
        				statsStage = new StatsDisplay();
        				statsStageInit = true; 
        				statsStage.update(statsCritters);
        			} else {
        				statsStage.update(statsCritters);
        			}
	            	
	            	
	            }
	        });
	        grid.add(statsBtn, 10, 31);
	        
	        /** seed */
			Label seed = new Label("Set Seed");
			grid.add(seed, 0, 50);
			Label seedLabel = new Label("Seed Number:");
			grid.add(seedLabel, 0, 51);
			TextField seedField = new TextField();
			grid.add(seedField, 1, 51);
			seedField.setMaxWidth(60);
			Button seedBtn = new Button();
	        seedBtn.setText("Set");
	        Text wrongSeedInput = new Text("Invalid Number!");
			wrongSeedInput.setFont(Font.font("Arial",FontWeight.NORMAL,12));
			wrongSeedInput.setFill(Color.RED);
			
	        seedBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(grid.getChildren().contains(wrongSeedInput)) {
	            		grid.getChildren().remove(wrongSeedInput);
	            	}
	            	String num = null;
	            	int numInt = 0;
	            	if((seedField.getText() != null && !seedField.getText().isEmpty())) {
	            		num = seedField.getText();
	            		num = num.trim();
	            		try{
	            			numInt = Integer.parseInt(num);
	            			Critter.setSeed(numInt);
	            		} catch(NumberFormatException e) {
	            			grid.add(wrongSeedInput, 1, 52);
	            		}
	            	}
	            }
	        });
	            	

			
	        seedBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(grid.getChildren().contains(wrongSeedInput)) {
	            		grid.getChildren().remove(wrongSeedInput);
	            	}
	            	String num;
	            	if((seedField.getText() != null && !seedField.getText().isEmpty())) {
	            		num = seedField.getText();
	            		num = num.trim();
	            		// need a try block for invalid number
	            		try{
	            			int numInt = Integer.parseInt(num);
	            		} catch(NumberFormatException e) {
	            			grid.add(wrongSeedInput, 1, 53);
	            		}
	            	}
	            }
	        });
	        grid.add(seedBtn, 1, 52);
	        
	        
	        /** quit */
			Button quitBtn = new Button();
	        quitBtn.setText("Quit");
			
	        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	System.exit(1); // TODO might probably need to change this
	            }
	        });
	        grid.add(quitBtn, 10, 70);
	        
	        
	        /** animation */
	        AnimationTimer timer = new AnimationTimer() { 
	            private long lastUpdate = 0 ;
	            @Override
	            public void handle(long now) {
	                    if (now - lastUpdate >= speed) {
	                    	// TODO perform what's needed here
	                    	Critter.worldTimeStep();
	                    	if(!worldStageInit) {
	            				worldStage = new WorldDisplay();
	            				worldStageInit = true; 
	            			} else {
	            				worldStage.update();
	            			}
	                    	if(!statsStageInit) {
	            				statsStage = new StatsDisplay();
	            				statsStageInit = true; 
	            				statsStage.update(statsCritters);
	            			} else {
	            				statsStage.update(statsCritters);
	            			}
	                        lastUpdate = now ;
	                    }
	            }
	        };
	        
	        Label animate = new Label("Critter Animation");
			grid.add(animate, 0, 70);
			Label animateSpeed = new Label("Animation Speed:");
			grid.add(animateSpeed, 0, 71);
	        Slider slider = new Slider();
	        slider.setMin(0);
	        slider.setMax(100);
	        slider.setValue(1);
	        slider.setShowTickLabels(true);
	        slider.setShowTickMarks(true);
	        slider.setMajorTickUnit(50);
	        slider.setMinorTickCount(5);
	        slider.setBlockIncrement(10);
	        grid.add(slider, 1, 71);
	        Button startAnimateBtn = new Button();
	        startAnimateBtn.setText("Start Animation");
	        Button stopAnimateBtn = new Button();
	        stopAnimateBtn.setText("Stop Animation");
	        stopAnimateBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	timer.stop();
	            	if(grid.getChildren().contains(stopAnimateBtn)) {
	            		grid.getChildren().remove(stopAnimateBtn);
	            	}
	            	makeBtn.setDisable(false);
	            	displayBtn.setDisable(false);
	            	stepBtn.setDisable(false);
	            	statsBtn.setDisable(false);
	            	seedBtn.setDisable(false);
	            }
	        });
	        startAnimateBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	double sliderVal = slider.getValue();
	            	speed = (2000_000_000 / sliderVal); // set speed of animation
	            	timer.start();
	            	grid.add(stopAnimateBtn, 1, 72);
	            	// TODO disable other buttons while this is happening
	            	makeBtn.setDisable(true);
	            	displayBtn.setDisable(true);
	            	stepBtn.setDisable(true);
	            	statsBtn.setDisable(true);
	            	seedBtn.setDisable(true);
	            }
	        });
	        grid.add(startAnimateBtn, 1, 72);
	        
	           
	        primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
        Critter.clearWorld(); // initialize the world map

        testOutputString = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(testOutputString);
        // Save the old System.out.
        old = System.out;
        // Tell Java to use the special stream; all console output will be redirected here from now
        System.setOut(ps);

		launch(args);
	}
}


