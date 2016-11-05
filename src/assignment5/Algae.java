/* CRITTERS Algae.java
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

/*
 * Do not change this file.
 */
import assignment5.Critter.TestCritter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Algae extends TestCritter {

	public String toString() { return "@"; }
	
	public Shape viewShape(int colLen, int rowLen) {
		Circle c = new Circle(Math.min(colLen,rowLen)/2);
		return c;
	}
	
	public Color viewOutlineColor() {
		return Color.LIMEGREEN;
	}
	
	public Color viewFillColor() {
		return Color.GREEN;
	}
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
}
