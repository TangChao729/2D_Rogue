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
 * Item class containing three kind of items and their interaction properties
 * with player.
 * May consider to separate each item into its own class and make this abstract
 * However, given the limited item kinds now, can be contained into one class.
 */

public class Item extends Entity {
	
	// constructor
	public Item (String eName, int xAxis, int yAxis) {
		
		super(eName, xAxis, yAxis);
		
	}
	
	@Override
	/*
	 *  since items are represented in different style, override this
	 */
	public char getMapCell() {
		
		if (this.getName().equals("HealingPotion")) {
			return '+';
		} else if (this.getName().equals("DamagePerk")) {
			return '^';
		} else if (this.getName().equals("WarpStone")) {
			return '@';
		}
		return '?'; 
		// return ? meaning something is wrong, placeholder, will not be 
		// accessed if code is right
		
	}
	
	/*
	 * Interaction differences from item to item
	 */
	
	public void interact(Player aPlayer) {
		
		// fully heal player HP to max
		if (this.getName().equals("HealingPotion")) {
			System.out.println("Healed!");
			aPlayer.setCurrHP(aPlayer.getMaxHP());
		} 
		
		// increase player damage by 1
		else if (this.getName().equals("DamagePerk")) {
			System.out.println("Attack up!");
			aPlayer.upDamage();
		} 
		
		// increase player level by 1
		else if (this.getName().equals("WarpStone")) {
			System.out.println("World complete! (You leveled up!)");
			aPlayer.levelUp();
		}
		
	}
	
}
