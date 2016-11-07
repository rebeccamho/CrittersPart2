/* CRITTERS Critter.java
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
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	public static List<List<Critter>> worldLists = new ArrayList<List<Critter>>(Params.world_height*Params.world_width);
	//private static int[][] worldCount = new int[Params.world_height][Params.world_width];
	private static boolean worldInitialized = false;
	private boolean movedThisTurn;
	private boolean inAFight;
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private int new_x_coord;
	private int new_y_coord;
	
	protected final String look(int direction, boolean steps) { 
		//int originalX = x_coord; 
		//int originalY = y_coord; 
		
		if(steps == false) {	// critter is going to walk (1 step)
			walk(direction);
		} else if(steps == true) {	// critter is going to run (2 steps)
			run(direction); 
		}
		
		int index = this.convertCoordToIndex(); 
		
		//x_coord = originalX; 
		//y_coord = originalY; 
		energy = energy - Params.look_energy_cost; 
		
		if(worldLists.get(index).size() != 0) {
			return worldLists.get(index).get(0).toString();
		} else {
			return null; 
		}
	}
	
	/**
	 * This method moves the critter one step in a specified direction and subtracts
	 * from the critter's energy the cost of walking plus the cost of rest.
	 * @param direction is the direction the critter will move in.
	 */
	protected final void walk(int direction) {
		if(movedThisTurn) { // Critter has already moved this turn
			energy = energy - Params.walk_energy_cost;
			return;
		}
		
		movedThisTurn = true;		
		
		int steps = 1; // 1 step for walking
		if(direction == 0) { // move right
			moveRight(steps);
		} else if(direction == 1) { // move diagonally up and to right
			moveRight(steps);
			moveUp(steps);
		} else if(direction == 2) { // move up
			moveUp(steps);
		} else if(direction == 3) { // move diagonally up and to left
			moveLeft(steps);
			moveUp(steps);
		} else if(direction == 4) { // move left
			moveLeft(steps);
		} else if(direction == 5) { // move diagonally down and to left
			moveLeft(steps);
			moveDown(steps);
		} else if(direction == 6) { // move down
			moveDown(steps);
		} else if(direction == 7) { // move move diagonally down and to right
			moveRight(steps);
			moveDown(steps);
		}
		
		energy = energy - Params.walk_energy_cost;
		
		if(inAFight) { // Critter is in a fight, need to check if space is occupied
			int index = convertNewCoordToIndex(); // space where Critter wants to move
			if(worldLists.get(index).size() == 0) { // space is not occupied
				addCritterToSpace();
			} 
		}
	}
	
	/**
	 * This method moves the critter two steps in a specified direction and subtracts
	 * from the critter's energy the cost of running plus the cost of rest.
	 * @param direction is the direction the critter will move in.
	 */
	protected final void run(int direction) {
		if(movedThisTurn) { // Critter has already moved this turn
			energy = energy - Params.run_energy_cost;
			return;
		}
		
		movedThisTurn = true;
		
		int steps = 2; // 2 steps for running
		if(direction == 0) { // move right
			moveRight(steps);
		} else if(direction == 1) { // move diagonally up and to right
			moveRight(steps);
			moveUp(steps);
		} else if(direction == 2) { // move up
			moveUp(steps);
		} else if(direction == 3) { // move diagonally up and to left
			moveLeft(steps);
			moveUp(steps);
		} else if(direction == 4) { // move left
			moveLeft(steps);
		} else if(direction == 5) { // move diagonally down and to left
			moveLeft(steps);
			moveDown(steps);
		} else if(direction == 6) { // move down
			moveDown(steps);
		} else if(direction == 7) { // move move diagonally down and to right
			moveRight(steps);
			moveDown(steps);
		}
		
		energy = energy - Params.run_energy_cost;
		if(inAFight) { // Critter is in a fight, need to check if space is occupied
			int index = convertNewCoordToIndex(); // space where Critter wants to move
			if(worldLists.get(index).size() == 0) { // space is not occupied
				addCritterToSpace();
			} 
		}
	}
	
	/**
	 * This method moves the critter one step in a specified direction. No energy
	 * is subtracted and the critter is not placed on the map.
	 * @param direction is the direction the critter will move in.
	 */
	private final void setAdjacent(int direction) {		
		int steps = 1; // 1 step for walking
		if(direction == 0) { // move right
			moveRight(steps);
		} else if(direction == 1) { // move diagonally up and to right
			moveRight(steps);
			moveUp(steps);
		} else if(direction == 2) { // move up
			moveUp(steps);
		} else if(direction == 3) { // move diagonally up and to left
			moveLeft(steps);
			moveUp(steps);
		} else if(direction == 4) { // move left
			moveLeft(steps);
		} else if(direction == 5) { // move diagonally down and to left
			moveLeft(steps);
			moveDown(steps);
		} else if(direction == 6) { // move down
			moveDown(steps);
		} else if(direction == 7) { // move move diagonally down and to right
			moveRight(steps);
			moveDown(steps);
		}
	}
	
	/**
	 * This method moves the critter a specified number of steps to the right.
	 * @param steps is number of steps the critter will move.
	 */
	private final void moveRight(int steps) {
		if(x_coord >= Params.world_width - steps) { // critter will go over right edge of board
			new_x_coord = x_coord - (Params.world_width - steps);
		} else {
			new_x_coord = x_coord + steps;
		}
	}
	
	/**
	 * This method moves the critter a specified number of steps to the left.
	 * @param steps is number of steps the critter will move.
	 */
	private final void moveLeft(int steps) {
		if(x_coord < steps) { // critter will go over left edge of board
			new_x_coord = Params.world_width - (steps - x_coord);
		} else {
			new_x_coord = x_coord - steps;
		}
	}
	
	/**
	 * This method moves the critter a specified number of steps up.
	 * @param steps is number of steps the critter will move.
	 */
	private final void moveUp(int steps) {
		if(y_coord < steps) { // critter will go over top edge of board
			new_y_coord = Params.world_height - (steps - y_coord);
		} else {
			new_y_coord = y_coord - steps;
		}
	}
	
	/**
	 * This method moves the critter a specified number of steps down.
	 * @param steps is number of steps the critter will move.
	 */
	private final void moveDown(int steps) {
		if(y_coord >= Params.world_height - steps) { // critter will go over bottom edge of board
			new_y_coord = y_coord - (Params.world_height - steps);
		} else {
			new_y_coord = y_coord + steps;
		}
	}
	
	/** 
	 * This method takes a critter's offspring and updates its position and adds
	 * it to the list of offspring for that world time step.
	 * @param offspring is the Critter to be added to list babies.
	 * @param direction is where the offspring should be relative to its parent.
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy) { return; } // parent doesn't have enough energy
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.setAdjacent(direction); // move offspring's position in specified direction relative to parent
		offspring.energy = this.energy/2; // fractions round down
		this.energy = (this.energy + 1)/2; // fractions round up
		babies.add(offspring);	
	}

	/**
	 * This method removes a Critter from its current space in the world.
	 */
	private final void removeCritterFromSpace() {
		int index = this.convertCoordToIndex();
		if(worldLists.get(index).contains(this)) {
			System.out.println(worldLists.get(index).size());
			worldLists.get(index).remove(this);
			System.out.println(worldLists.get(index).size());

		}
	}
	
	/**
	 * This method adds a Critter to the space corresponding to the Critter's
	 * x and y coordinates.
	 */
	private final void addCritterToSpace() {
		int index = this.convertNewCoordToIndex();
		worldLists.get(index).add(this);
		this.x_coord = this.new_x_coord;
		this.y_coord = this.new_y_coord;
	}
	
	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			String class_name = myPackage + "." + critter_class_name;
			Critter newCritter = (Critter) Class.forName(class_name).newInstance();
			
			newCritter.energy = Params.start_energy;
			newCritter.new_x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.new_y_coord = Critter.getRandomInt(Params.world_height);
			newCritter.addCritterToSpace();
			population.add(newCritter);

				
		} catch(ClassNotFoundException e) {
			//throw e;
			throw new InvalidCritterException(critter_class_name);
		} catch(InstantiationException | IllegalAccessException e1){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	

//	public Shape viewShape(int colLen, int rowLen) {
//		Rectangle r = new Rectangle(colLen,rowLen);
//		return r;
//	}
	
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException { // TODO finish this
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			String class_name = myPackage + "." + critter_class_name;
			Critter testCritter = (Critter) Class.forName(class_name).newInstance();
			for(Critter crit : population) {
				if(testCritter.getClass().isInstance(crit)) {
					result.add(crit);
				}
			}
//			for(int i = 0; i < population.size(); i++) {
//				String name = population.get(i).getClass().getName();
//				if(name.equals(class_name)) {
//					result.add(population.get(i));
//				}
//			}
		} catch(ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch(InstantiationException | IllegalAccessException e1){
			throw new InvalidCritterException(critter_class_name);
		}

		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) { // TODO test this
			super.removeCritterFromSpace();
			super.x_coord = new_x_coord;
			super.addCritterToSpace();
		}
		
		protected void setY_coord(int new_y_coord) { // TODO test this
			super.removeCritterFromSpace();
			super.y_coord = new_y_coord;
			super.addCritterToSpace();
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}


	/**
	 * Clear the world of all critters, dead and alive
	 * This method initializes the world matrix so that all spaces are equal to one
	 * whitespace.
	 */
	public static void clearWorld() {
		if(!worldInitialized) { // no lists exist yet
			initializeWorldLists();
		} else { // clear all lists
			int size = worldLists.size();
			for(int i = 0; i < size; i++) {
				worldLists.get(i).clear();
			}
		}
	}
	
	public static void worldTimeStep() {		
		for(int i = 0; i < population.size(); i++) {
			Critter current = population.get(i);
			current.movedThisTurn = false; // reset movedThisTurn
			current.doTimeStep();
			current.energy = current.energy - Params.rest_energy_cost;
			
		}
		for(int i = 0; i < population.size(); i++) { // now add all Critters to the space they moved to
			Critter current = population.get(i);
			if(current.energy <= 0) { // kill critter
				current.removeCritterFromSpace(); // remove Critter from its space
				population.remove(current); // remove Critter from population
				i = i - 1; // all Critters will be shifted left, so need to update i accordingly
			} else if(current.movedThisTurn){
				current.removeCritterFromSpace();
				current.addCritterToSpace();
			}
		}
		
		
		/* handle encounters between Critters */
		for(int i = 0; i < worldLists.size(); i++) { 
			while(worldLists.get(i).size() > 1) {
				List<Critter> space = worldLists.get(i);
				Critter critterOne = space.get(0);
				Critter critterTwo = space.get(1);
				critterOne.inAFight = true;
				critterTwo.inAFight = true;
				boolean fightOne = critterOne.fight(critterTwo.toString());
				boolean fightTwo = critterTwo.fight(critterOne.toString());
				if(critterOne.energy <= 0) { // critterOne died trying to walk/run
					population.remove(critterOne);
					critterOne.removeCritterFromSpace();
					critterTwo.inAFight = false;
				} else if(critterTwo.energy <= 0) { // critterTwo died trying to walk/run
					population.remove(critterTwo);
					critterTwo.removeCritterFromSpace();
					critterOne.inAFight = false;
				} else if(critterOne.x_coord == critterTwo.x_coord && critterOne.y_coord == critterTwo.y_coord) { // Critters did not die/move away
					int randOne = 0;
					int randTwo = 0;
					if(fightOne) { randOne = Critter.getRandomInt(critterOne.energy); }
					if(fightTwo) { randTwo = Critter.getRandomInt(critterTwo.energy); }
					if(randOne >= randTwo) { // CritterOne wins
						critterOne.energy += (critterTwo.energy)/2;
						critterOne.inAFight = false; // reset inAFight
						critterTwo.removeCritterFromSpace();
						population.remove(critterTwo);
					} else { // CritterTwo wins
						critterTwo.energy += (critterOne.energy)/2;
						critterTwo.inAFight = false; // reset inAFight
						critterOne.removeCritterFromSpace();
						population.remove(critterOne);
					}
				}	
			}
		}
		
		/* create Params.refresh_algae_count new Algae */ 
		try {
			for(int j = 0; j < Params.refresh_algae_count; j++) {
				Critter.makeCritter("Algae");
			}
		} catch(InvalidCritterException e) {
			System.out.println(e.toString());
		}
		
		/* add babies to population */
		int size = babies.size();
		for(int i = 0; i < size; i++) {
			Critter baby = babies.get(0);
			population.add(baby); // add first baby in list to population
			baby.addCritterToSpace(); // add baby to map
			babies.remove(0); // remove baby from babies list
		}
	}
	
	
	/**
	 * This method prints the world.
	 */
	public static void displayWorld() {
		System.out.print("+"); 
		for(int c = 0; c < Params.world_width; c++) { 
			System.out.print("-");
		}
		System.out.println("+"); 
		for(int r = 0; r < Params.world_height; r++) { 
			System.out.print("|");
			for(int c = 0; c < Params.world_width; c++) {
				int index = convertCoordToIndex(r,c);
				if(worldLists.get(index).size() > 0) {
					System.out.print(worldLists.get(index).get(0));
				} else {
					System.out.print(" ");
				}
			}
			System.out.println("|");
		}
		System.out.print("+"); 
		for(int c = 0; c < Params.world_width; c++) { 
			System.out.print("-");
		}
		System.out.println("+");
	}
	
	private final int convertCoordToIndex() {
		int index = this.y_coord*Params.world_width + this.x_coord;
		return index;
	}
	
	private final int convertNewCoordToIndex() {
		int index = this.new_y_coord*Params.world_width + this.new_x_coord;
		return index;
	}
	
	private static int convertCoordToIndex(int row, int col) {
		int index = row*Params.world_width + col;
		return index;
	}
	
	private static void initializeWorldLists() {
		for(int i = 0; i < Params.world_height*Params.world_width; i++) {
			List<Critter> newList = new ArrayList<Critter>();
			worldLists.add(newList);
		}
		worldInitialized = true;
	}
}
