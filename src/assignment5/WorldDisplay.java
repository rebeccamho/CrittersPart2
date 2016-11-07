package assignment5;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;




public class WorldDisplay extends Stage {
	static GridPane grid = new GridPane();
	Label x = new Label("Second stage");
	
	int rows = Params.world_height;
	int cols = Params.world_width;
	int worldHeight = 700; 
	int worldWidth = 700;
	int rowLen = (int) Math.floor(worldHeight/rows);
	int colLen = (int) Math.floor(worldWidth/cols);
	
	WorldDisplay(){
	    
		this.setTitle("Critter World Grid -- Second Stage");
		grid.setGridLinesVisible(true);


		grid.getStyleClass().add("critter-grid");
		
		for(int i = 0; i < cols; i++) {
			ColumnConstraints col = new ColumnConstraints(colLen);
			grid.getColumnConstraints().add(col);
		}
		
		for(int i = 0; i < rows; i++) { 
			RowConstraints row = new RowConstraints(rowLen);
			grid.getRowConstraints().add(row);
		}
		
		Scene scene = new Scene(grid, worldWidth, worldHeight, Color.WHITE);
		this.setScene(scene);
		//this.show();
		update();
	   } 
	
	public void update() { 
		for(int r = 0; r < Params.world_height; r++) { 
			for(int c = 0; c < Params.world_width; c++) {
				int index = convertCoordToIndex(r,c);
				if(Critter.worldLists.get(index).size() > 0) {
					Critter crit = Critter.worldLists.get(index).get(0);
					Critter.CritterShape sCrit = crit.viewShape();
					Shape s = getShape(sCrit); 
					
					s.setFill(crit.viewFillColor());
					s.setStroke(crit.viewOutlineColor());
					this.grid.add(s, c, r); // add the shape to the grid.
				} 
			}
		}
		this.show();
	}
	
	
	private final int convertCoordToIndex(int r, int c) {
		int index = r*Params.world_width + c;
		return index;
	}
	
	private final Shape getShape(Critter.CritterShape critShape) {
//		if(sCrit.equals(Critter.CritterShape.SQUARE)) {
//			s = new Rectangle(colLen,rowLen);
//		} else if {
//			
//		}
		return null;
	}

}
