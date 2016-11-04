/* CRITTERS Critter3.java
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
 * Critter3 has 3 genes that determine its direction each time step.
 * It has a 50% chance of reproducing each time step (if it's energy is high enough).
 * It does not move unless in a fight.
 * When in a fight, it tries to run. If it cannot run, it will fight.
 */

package assignment5;

public class Critter3 extends Critter {

	private static final int GENE_TOTAL = 16;
	private int[] genes = new int[3];
	private int numGenes = 3;
	private int dir;
	
	public Critter3() {
		for (int k = 0; k < numGenes; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	@Override
	public String toString() { return "3"; }
	
	@Override
	public void doTimeStep() {

		int reproduce = Critter.getRandomInt(2); // will equal 0 or 1 - 50% chance Critter3 will reproduce
		if (reproduce == 1) {
			Critter3 child = new Critter3();
			for (int k = 0; k < numGenes; k += 1) {
				child.genes[k] = this.genes[k] + 1;
			}
			reproduce(child, Critter.getRandomInt(8));
		}
		
	}

	@Override
	public boolean fight(String opponent) {
		Algae test = new Algae();
		if(opponent.equals(test.toString())) { return true; } // try to eat Algae
		run(dir);
		
		/* pick a new direction based on our genes */
		int total = 0;
		for(int k = 0; k < numGenes; k+=1) {
			total = total + genes[k];
		}
		dir = total % 8;
		
		return true;
	}

}
