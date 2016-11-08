/* CRITTERS WorldDisplay.java
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

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;




public class WorldDisplay extends Stage {
	
	static GridPane grid = new GridPane();
	Label x = new Label("Second stage");
	
	ArrayList<ShapeGrid> shapeList = new ArrayList<ShapeGrid>();
	
	int rows = Params.world_height;
	int cols = Params.world_width;
	int worldHeight = 800; 
	int worldWidth = 800;
	int rowLen = (int) Math.floor(worldHeight/rows);
	int colLen = (int) Math.floor(worldWidth/cols);
	
	WorldDisplay(){
	    
		this.setTitle("Critter World Grid Stage");
		grid.setGridLinesVisible(false);


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
				Shape prevShape = getPreviousShape(r,c);
				if(prevShape != null) {
					grid.getChildren().remove(prevShape);
				}
				int index = convertCoordToIndex(r,c);
				if(Critter.worldLists.get(index).size() > 0) {
					Critter crit = Critter.worldLists.get(index).get(0);
					Critter.CritterShape sCrit = crit.viewShape();
					Shape s = getShape(sCrit); 
					ShapeGrid sg = new ShapeGrid(s,c,r);
					shapeList.add(sg);
					s.setFill(crit.viewFillColor());
					s.setStroke(crit.viewOutlineColor());
					grid.add(s, c, r); // add the shape to the grid.
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
		Shape s = null;
		double cLen = (double) colLen-1; 
		double rLen = (double) rowLen-1;
		if(critShape.equals(Critter.CritterShape.CIRCLE)) {
			s = new Circle(Math.min(colLen-1,rowLen-1)/2);
		} else if(critShape.equals(Critter.CritterShape.SQUARE)) {
			s = new Rectangle(colLen-1,rowLen-1);
		} else if(critShape.equals(Critter.CritterShape.TRIANGLE)) {
			Polygon p = new Polygon();
			p.getPoints().addAll(0.0, rLen, cLen/2, 0.0, cLen, rLen);
			s = p; 
		} else if(critShape.equals(Critter.CritterShape.DIAMOND)) {
			Polygon p = new Polygon(); 
			p.getPoints().addAll(cLen/2, 0.0, cLen, rLen/2, cLen/2, rLen, 0.0, rLen/2);
			s = p;
		} else if(critShape.equals(Critter.CritterShape.STAR)) {
			Polygon p = new Polygon(); 
			p.getPoints().addAll(cLen/2, 0.0, cLen*7/10, rLen*1/3, cLen, rLen*1/3, cLen*3/4, rLen*3/5, cLen*9/10, rLen, cLen/2, rLen*8/10, cLen*1/10, rLen, cLen*1/4, rLen*3/5, 0.0, rLen*1/3, cLen*3/10, rLen*1/3);
			//p.getPoints().addAll(cLen/2, 0.0, 0.0, rLen, cLen, rLen/3, 0.0, rLen/3, cLen, rLen);
			s = p;
		}
		return s;
	}
	
	private final Shape getPreviousShape(int r, int c) {
		for(int i = 0; i < shapeList.size(); i++) {
			if(shapeList.get(i).row == r && shapeList.get(i).col == c) {
				Shape s = shapeList.get(i).shape;
				shapeList.remove(i);
				return s;
			}
		}
		return null;
	}

}
