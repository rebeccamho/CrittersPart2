/* CRITTERS Critter4.java
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

/** 
 * Critter4 has 6 genes that determine its direction each time step.
 * It has a 25% chance of reproducing each time step (if it's energy is high enough).
 * When in a fight, it does not run and tries to fight.
 */

package assignment5;

import assignment5.Critter.CritterShape;

public class Critter4 extends Critter {

	private int[] genes = new int[6];
	private int numGenes = 6;
	private int dir;
	
	public Critter4() {
		for (int k = 0; k < numGenes; k += 1) {
			genes[k] = Critter.getRandomInt(4);
		}
		dir = Critter.getRandomInt(8);
	}
	
	@Override
	public CritterShape viewShape() {
		return Critter.CritterShape.SQUARE; 
	}
	
	@Override
	public String toString() { return "4"; }
	
	@Override
	public void doTimeStep() {
		run(dir);
		
		int reproduce = Critter.getRandomInt(4); // 25% chance Critter4 will reproduce
		if (reproduce == 1) {
			Critter4 child = new Critter4();
			for (int k = 0; k < numGenes; k += 1) {
				child.genes[k] = Critter.getRandomInt(4);
			}
			reproduce(child, dir);
		}
		
		/* pick a new direction from genes */
		int total = 0;
		for(int k = 0; k < numGenes; k+=1) {
			total = total + 3*genes[k];
		}
		dir = total % 8;
		
	}

	@Override
	public boolean fight(String opponent) {
		Algae test = new Algae();
		if(opponent.equals(test.toString())) { return true; } // try to eat Algae
		
		return true;
	}

}
