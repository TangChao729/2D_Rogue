import java.util.LinkedHashMap;

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
 *  Monster class, provide monster initiation, and variable setting.
 */

public class Monster extends Unit{

	private int monsterDamage;
	
	// used in AS1 version only
	final static int DEF_X = 4;
	final static int DEF_Y = 2;
	
	// default movement amount and monster detection range
	final static int MOVEAMOUNT = 1;
	final static int DETECT_RANGE = 2;

	
	/*
	 *  This is the monster constructor for AS1 version, under the monster menu
	 *  Construct single monster with default location and custom name, health and damage
	 */

	public Monster (String aMonsterName, int aMonsterMaxHP, int aMonsterDamage) {
		
		super(aMonsterName, DEF_X, DEF_Y, aMonsterMaxHP, aMonsterMaxHP);
		setDamage(aMonsterDamage);
		
	}
	
	/*
	 *  Overload constructor for AS2 version, monsters evoked in SaveLoad class
	 *  with all parameters read from file
	 */

	public Monster (String aMonsterName, int DEF_X, int DEF_Y, int aMonsterMaxHP, int aMonsterDamage) {
		
		super(aMonsterName, DEF_X, DEF_Y, aMonsterMaxHP, aMonsterMaxHP);
		setDamage(aMonsterDamage);
		
	}
	
	/*
	 * Monster to move based Player's location and map terrain
	 * 
	 * MOVEING PRIORITIES:
	 * EAST & WEST first
	 * NORTH & SOUTH second
	 */
	
	public void monsterMove(Map currMap, Player currPlayer) {
		
		// naming variables 
		int monsterX = this.getLocation()[0];
		int monsterY = this.getLocation()[1];
		int playerX = currPlayer.getLocation()[0];
		int playerY = currPlayer.getLocation()[1];
	
		LinkedHashMap<String, int[]> validMoves = getValidMoves(currMap, currPlayer);

		// check if player is within range from monster
		if (monsterX >= playerX - DETECT_RANGE && monsterX <= playerX + DETECT_RANGE 
				&& monsterY >= playerY - DETECT_RANGE && monsterY <= playerY + DETECT_RANGE) {
			
			// get original distance d1 from monster to player
			int currDistance = this.getDistance(currPlayer);
			
			String nextMove = "stay";
			Integer nextMoveDistance;
			
			// this variable may or may not be updated depends on the situation
			@SuppressWarnings("unused")
			Integer minDistance = currDistance;
			
			// check each valid move and its distance to player
			for (String s : validMoves.keySet()) {
				nextMoveDistance = Math.abs(playerX - validMoves.get(s)[0]) 
						+ Math.abs(playerY - validMoves.get(s)[1]);
				
				// pick the valid move that brings the monster closer to player
				if (nextMoveDistance < currDistance) {
					minDistance = nextMoveDistance;
					nextMove = s;
				}

			}
			
			// if none of valid move can get monster closer to player, stay
			// else set monster with the correct move to player.
			if (!nextMove.equals("stay")) {
				this.setLocation(validMoves.get(nextMove)[0], validMoves.get(nextMove)[1]);
				
			}	
			
		}
		
	}
	
	/*
	 * Helper method for monsterMove()
	 * check each of four adjacent cell from monster, check if cell is traversable
	 * if true, add direction as key and cell x, y location as value to LinkedHashMap
	 * 
	 * a LinkedHashMap helps to keep North & South direction iterated first in monsterMove(), then West & East
	 * so if moving horizontally or vertically takes monster to a same distance to player,
	 * monster will always move horizontally as these two direction get iterated last, so updated at the end
	 */
	
	private LinkedHashMap<String, int[]> getValidMoves(Map currMap, Player currPlayer) {
		
		// naming variables 
		int monsterX = this.getLocation()[0];
		int monsterY = this.getLocation()[1];
		
		LinkedHashMap<String, int[]> validMoves = new LinkedHashMap<String, int[]>();
		
		// Check if movement is in bounds - if so move, otherwise stay hold.
		if (monsterY - MOVEAMOUNT >= 0) {
			
			// check if movement is valid e.g. blocked by obstacles
			if (currMap.checkTraversable(monsterX, monsterY - MOVEAMOUNT)) {
				
				// if true. Add to validMoves
				int[] moveNorth = {monsterX, monsterY-MOVEAMOUNT};
				validMoves.put("North", moveNorth);
			}
			
		}
		
		// Check if movement is in bounds - if so move, otherwise stay hold.
		if (monsterY + MOVEAMOUNT < currMap.getMapHeight()) {
			
			// check if movement is valid e.g. blocked by obstacles
			if (currMap.checkTraversable(monsterX, monsterY + MOVEAMOUNT)) {
				
				// if true. Add to validMoves
				int[] moveSouth = {monsterX, monsterY + MOVEAMOUNT};
				validMoves.put("South", moveSouth);
			}
			
		}
		
		// Check if movement is in bounds - if so move, otherwise stay hold.
		if (monsterX - MOVEAMOUNT >= 0) {
			
			// check if movement is valid e.g. blocked by obstacles
			if (currMap.checkTraversable(monsterX - MOVEAMOUNT, monsterY)) {
				
				// if true. Add to validMoves
				int[] moveEast = {monsterX - MOVEAMOUNT, monsterY};
				validMoves.put("West", moveEast);
			}
			
		}
		
		// Check if movement is in bounds - if so move, otherwise stay hold.
		if (monsterX + MOVEAMOUNT < currMap.getMapWidth()) {
			
			// check if movement is valid e.g. blocked by obstacles
			if (currMap.checkTraversable(monsterX + MOVEAMOUNT, monsterY)) {
				
				// if true. Add to validMoves
				int[] moveWest = {monsterX + MOVEAMOUNT, monsterY};
				validMoves.put("East", moveWest);
			}
			
		}
		
		return validMoves;
		
	}
	
	// override as initial letter case differ
	@Override
	public char getMapCell() {
		return Character.toLowerCase(this.getName().charAt(0));
	}
	
	/*
	 *  setters & getters
	 */

	private void setDamage(int aMonsterDamage) {
		this.monsterDamage = aMonsterDamage;
	}

	public int getDamage() {
		return this.monsterDamage;
	}
	
}
