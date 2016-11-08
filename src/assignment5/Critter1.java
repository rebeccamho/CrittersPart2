/* CRITTERS Critter1.java
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

import javafx.scene.shape.*;
import javafx.scene.paint.*;


/*
 * to begin, Critter1 gets randomly assigned an initial direction
 * during doTimeStep, Critter1 has an equal and random chance of walking, running, and not moving; only reproduces if it has 5 times reproduce energy 
 * during fight, Critter1 runs if it has enough energy to do so 
 */
public class Critter1 extends Critter {
	@Override
	public String toString() { return "1"; }
	
	private int dir;
	
	
	public Critter1() {
		dir = Critter.getRandomInt(8);
	}
	
	
	@Override
	public CritterShape viewShape() {
		return Critter.CritterShape.STAR; 
	}
	
	public Color viewOutlineColor() {
		return Color.BLUE;
	}
	
	public Color viewFillColor() {
		return Color.AQUA;
	}
	
	public boolean fight(String not_used) { 
		/* runs if it has not already moved and new location has Algae or no other Critter*/ 
		if(hasMoved() == false) {
			String critterName = look(dir,true);
			if(critterName == null || critterName == "@") {
				run(dir); 
			}
			dir = Critter.getRandomInt(8);
			return false;
		}
		
		/* otherwise, tries to fight */ 
		return true; 
	}

	@Override
	public void doTimeStep() {
		if(getEnergy() > Params.rest_energy_cost + Params.run_energy_cost) {
			int move = Critter.getRandomInt(3);		// 3 options: walk, run, don't move
			if(move == 0) {		// Critter1 walks
				String critterName = look(dir,false);
				if(critterName == null || critterName == "@") {
					walk(dir);
				}
				dir = Critter.getRandomInt(8);
			} else if(move == 1) {
				String critterName = look(dir,true);
				if(critterName == null || critterName == "@") {
					run(dir); 
				}
				dir = Critter.getRandomInt(8);
			}
		}
		
		if(getEnergy() > Params.min_reproduce_energy*2) {
			Critter1 child = new Critter1(); 
			reproduce(child,Critter.getRandomInt(8));
		}
		

	}
	


}
