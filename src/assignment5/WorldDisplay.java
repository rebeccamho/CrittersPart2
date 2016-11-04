package assignment5;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorldDisplay extends Stage {
	Label x = new Label("Second stage");
	VBox y = new VBox();
	
	WorldDisplay(){
	    y.getChildren().add(x);
	    this.setScene(new Scene(y, 300, 300));
	    this.show();
	   }  
}
