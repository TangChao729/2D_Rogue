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
 *  Player class, provide player initiation, and variable setting.
 *  Contain unique attributes such as player level, and level-up interactions
 */

public class Player extends Unit{

	private int playerLevel;
	private int playerDamage;
	
	final static int BASE_HP = 17;
	final static int LEVEL_HP = 3;
	final static int BASE_DAMAGE = 1;
	
	// for AS1 version only
	final static int DEF_X = 1;
	final static int DEF_Y = 1;
	
	// default level up and damage up amount
	final static int LEVEL_UP = 1;
	final static int DAMAGE_UP = 1;
		
	/*
	 *  Constructor 
	 *  CurrHp, MaxHp and damage all updated based on currLevel of player
	 *  New player always started from level 1
	 */
	public Player(String aPlayerName) {
		
		super(aPlayerName, DEF_X, DEF_Y, 0, 0);
		// new player default level
		setLevel(1); 
		
	}
	
	/*
	 * update player status
	 * set MaxHP based on level
	 * set damage based on level
	 */
	private void updateStatus() {
		
		this.setMaxHP(this.playerLevel * LEVEL_HP + BASE_HP);
		this.setCurrHP(this.getMaxHP());
		this.setDamage(this.getLevel());
		
	}
	
	/*
	 *  setters, set as public for use in loading player in Save&Load class
	 */
	public void setLevel(int aPlayerLevel) {
		
		this.playerLevel = aPlayerLevel;
		updateStatus();
		
	}


	private void setDamage(int aPlayerLevel) {
		
		this.playerDamage = aPlayerLevel + BASE_DAMAGE;
		
	}
	
	/*
	 * DamageUp when have damagePerk, used in item class
	 */
	public void upDamage() {
		
		this.playerDamage = playerDamage + DAMAGE_UP;
		
	}

	/*
	 * LevelUp when have warpStone, used in item class
	 */
	public void levelUp() {
		
		// levelUp by a amount
		this.setLevel(this.getLevel()+LEVEL_UP);
		
		// update Max health and damage
		updateStatus();
		
	}
	
	/*
	 *  getters
	 */
	public int getLevel() {
		
		return this.playerLevel;
	}
	
	
	public int getDamage() {
		
		return this.playerDamage;
		
	}
	
}
