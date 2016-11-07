/* CRITTERS StatsDisplay.java
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatsDisplay extends Stage {
	static GridPane grid = new GridPane();
	Label x = new Label("Statistics");
	
	StatsDisplay() {
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setHgap(5);
		grid.setVgap(2);
		grid.setPadding(new Insets(10, 10, 10, 10));
		Scene scene = new Scene(grid, 800, 300);
		this.setScene(scene);
		this.setTitle("Critter Statistics Stage");
		
		this.show();
	}
	
	public boolean update(ArrayList<String> names) {
		grid.getChildren().clear();
		Text header = new Text("Critter Statistics");
		header.setFont(Font.font("Arial",FontWeight.BOLD,14));
		grid.add(header, 0, 0, 2, 1);
		for(int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			try {
	    			String myPackage = Critter.class.getPackage().toString().split(" ")[1];
					List<Critter> critters = Critter.getInstances(name);
					String critter_class_name = myPackage + "." + name;
				         				
					Critter testCritter; 
					Class<?> testClass; 
					try {
	 				testCritter = (Critter) Class.forName(critter_class_name).newInstance();
	 				testClass = testCritter.getClass();
					} catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						throw new InvalidCritterException(critter_class_name);
					} 
					Method statsMethod = testClass.getMethod("runStats", List.class);
					statsMethod.invoke(testClass, critters);
					String text = Main.testOutputString.toString();
					Text statsOutput = new Text();
					statsOutput.setText(text);
					statsOutput.setFont(Font.font("Arial",FontWeight.NORMAL,12));
					Text nameCritter = new Text();
					nameCritter.setText(name + ": ");
					nameCritter.setFont(Font.font("Arial",FontWeight.NORMAL,12));
					grid.add(nameCritter, 0, 2*i+1);
					grid.add(statsOutput, 0, 2*i+2);
					Main.testOutputString.reset();
					
			} catch(InvalidCritterException | NoSuchMethodException | SecurityException | 
 					IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
 				//System.out.println(e.toString());
 				//grid.add(wrongStatsInput, 10, 33);
				return false;
			
 			}
		}
		this.show();
		return true;
	}
	
	
}
