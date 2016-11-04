package assignment5;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WorldDisplay extends Stage {
	static GridPane grid = new GridPane();
	Label x = new Label("Second stage");
	
	WorldDisplay(){
		grid.setGridLinesVisible(true);
	    grid.getChildren().add(x);
	    Scene scene = new Scene(grid, 500, 500);
	    this.setScene(scene);
	    
	    this.show();
	   }  
}
