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
 * Abstract class for all units.
 * Including properties:
 * 	maxHP
 * 	currHP
 */

public abstract class Unit extends Entity{

	private int maxHP;
	private int currHP;

		
	// Constructor
	public Unit (String eName, int xAxis, int yAxis, int maxHP, int currHP) {
		super(eName, xAxis, yAxis);
		setMaxHP(maxHP);
		setCurrHP(currHP);
	}
	
	/*
	 * setters & getters
	 */
	
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	
	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}
	
	public int getMaxHP() {
		return this.maxHP;
	}

	public int getCurrHP() {
		return this.currHP;
	}
	

}
