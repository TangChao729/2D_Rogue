/**
 * COMP90041 Assignment 2 - Rogue Expanded
 * 
 * Welcome to Rogue Expanded, the advanced version of the base game you implemented in Assignment 1. 
 * In this assignment, we will build on our existing game to add more features to make it a more fully-featured game.

 * 
 * @author: Taylor Tang 1323782 chaojunt@student.unimelb.edu.au
 *
 */

/*
 * Abstract class for all entities.
 * Containing entityName -> eName &
 * mapLocation -> [x, y]
 */

public abstract class Entity {
	
	/*
	 * Entity variables
	 */
	
	private String eName;
	private int[] mapLocation = new int[2];
	
	//Constructor
	public Entity (String eName, int xAxis, int yAxis) {
		this.eName = eName;
		setLocation(xAxis, yAxis);

	}
	
	
	/* 
	 * calculate L1 distance from one entity to another, without consider mapTerrain
	 */
	public int getDistance(Entity another) {
		
		// naming variables
		int currEntityX = this.getLocation()[0];
		int currEntityY = this.getLocation()[1];
		int anotEntityX = another.getLocation()[0];
		int anotEntityY = another.getLocation()[1];
		
		int distance = Math.abs(currEntityX - anotEntityX) + Math.abs(currEntityY - anotEntityY);
		
		return distance;
		
	}
	
	/*
	 * setter & getters
	 */
	
	public void setName(String name) {
		this.eName = name;
	}
	
	public void setLocation(int xAxis, int yAxis) {
		this.mapLocation[0] = xAxis;
		this.mapLocation[1] = yAxis;
	}
	
	public String getName() {
		return this.eName;
	}
	
	// get map cell based on first char of name
	public char getMapCell() {
		return this.eName.charAt(0);
	}
	
	// return location int[]
	public int[] getLocation() {
		return this.mapLocation;
	}

}
