/* CRITTERS Critter2.java
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
 * to begin, Critter2 has 64 genes that are equally distributed in 8 directions
 * Critter1 gets randomly assigned an initial direction
 * during doTimeStep, Critter1 only walks in cardinal directions if it has enough energy; reproduces if has 5 times amount of energy needed   
 * during fight, Critter1 fights if Algae is opponent, tries to run if it has enough energy, otherwise fights  
 */
public class Critter2 extends Critter {
	@Override
	public String toString() { return "2"; }
	
	private int dir;
	private static final int GENE_TOTAL = 64;
	private int[] genes = new int[8];

	
	public Critter2() {
		dir = Critter.getRandomInt(8);
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
	}
	
	public boolean fight(String opponent) { 
		/* if fighting Algae, tries to eat it */
		Algae name = new Algae();
		if(opponent.equals(name.toString())) {
			return true;
		}
		
		/* runs if it has not already moved and has enough energy */ 
		else if(this.getEnergy() > (Params.rest_energy_cost + Params.run_energy_cost)) {
			run(dir);
			dir = Critter.getRandomInt(8);
			return false;
		}
		
		/* otherwise, tries to fight */ 
		else 
			return true; 
	
	}	

	@Override
	public void doTimeStep() {
		/* runs if has enough energy and only walks in cardinal directions (up, down, left, right) */
		if((this.getEnergy() > Params.rest_energy_cost + Params.run_energy_cost) && (dir % 2) == 0) {
			walk(dir); 
			
			/* pick a new direction based on our genes */
			int roll = Critter.getRandomInt(GENE_TOTAL);
			int turn = 0;
			while (genes[turn] <= roll) {
				roll = roll - genes[turn];
				turn = turn + 1;
			}
			assert(turn < 8);
			
			dir = (dir + turn) % 8;
		} else { 
		}
		
		/* reproduces if has enough energy and has not moved during turn */
		if(getEnergy() > Params.min_reproduce_energy*5) {
			Critter2 child = new Critter2();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}

	}

}
