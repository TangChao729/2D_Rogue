import java.util.ArrayList;

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
 *  World and battle loop class, provide world initiation, and combat looping.
 */

public class World {
	
	// each world has one map
	private Map currMap;

	// Default participants for AS1 
	private Player player;
	private Monster monster;
	
	// Valid input options
	final static String MOVE_NORTH = "w";
	final static String MOVE_EAST = "d";
	final static String MOVE_SOUTH = "s";
	final static String MOVE_WEST = "a";
	final static String ABORT_WORLD = "home";
	
	// Default movement amount
	final static int MOVEAMOUNT = 1;
	
	/*
	 * Constructor, two kinds, one start AS1 old type
	 * with a player & a monster from main menu
	 */

	public World(Player aPlayer, Monster aMonster) {
		this.player = aPlayer;
		this.monster = aMonster;
		
		this.currMap = new Map(this.player, this.monster);
	}
	
	/* 
	 * One start AS2 new type world
	 * with Player & level from main menu,
	 * monsters, items, map from loaded file 
	 */
	
	public World(Map aMap) {
		this.currMap = aMap;
		this.player = currMap.getPlayer();
		//this.monsters = currMap.getMonsters();
		//this.items = currMap.getItems();
	}
	
	/*	
	 * Game logic goes here
	 * 1. World rendering done through map class
	 * 2. Monsters decide their movement
	 * 3. Player controlling through scanner input
	 * 4. Item interaction based on player's X&Y
	 * 5. combat based on player and monster's location
	 */

	public void explore() {
		
		while(true) {
			
			// 1. Draw the map based on current data
			this.currMap.drawMap();
			
			// 2. Monsters decide their movement
			for (Monster aMonster : this.currMap.getMonsters()) {
				aMonster.monsterMove(this.currMap, this.player);
			}
			
			// 3. Player controlling through scanner input
			System.out.println();
			
			// Print a user prompt.
			System.out.print("> ");
			
			// User input for controlling
			String moveDirection = GameEngine.keyboard.nextLine();
			//GameEngine.keyboard.nextLine();
			
			int currPlayerX = player.getLocation()[0];
			int currPlayerY = player.getLocation()[1];
			
			int mapWidth = currMap.getMapWidth();
			int mapHeight = currMap.getMapHeight();
			
			if(moveDirection.equalsIgnoreCase(MOVE_NORTH)) {
				
				// Check if movement is in bounds - if so move, otherwise stay hold.
				if(currPlayerY - MOVEAMOUNT >= 0) {
					
					// check if movement is valid e.g. blocked by obstacles
					if (currMap.checkTraversable(currPlayerX, currPlayerY - 1)) {
						
						// if true. Update movement.
						player.setLocation(currPlayerX, currPlayerY - 1);	
					}	
					
				} else {
					// do nothing
				}	
				
			} else if(moveDirection.equalsIgnoreCase(MOVE_EAST)) {
				
				// Check if movement is in bounds - if so move, otherwise stay hold.
				if(currPlayerX + MOVEAMOUNT < mapWidth) {
					
					// check if movement is valid e.g. blocked by obstacles
					if (currMap.checkTraversable(currPlayerX + 1, currPlayerY)) {
						
						// if true. Update movement.
						player.setLocation(currPlayerX + 1, currPlayerY);	
					}	
					
				} else {
					// do nothing
				}		
				
			} else if(moveDirection.equalsIgnoreCase(MOVE_SOUTH)) {

				// Check if movement is in bounds - if so move, otherwise stay hold.
				if(currPlayerY + MOVEAMOUNT < mapHeight) {
					
					// check if movement is valid e.g. blocked by obstacles
					if (currMap.checkTraversable(currPlayerX, currPlayerY + 1)) {
						
						// if true. Update movement.
						player.setLocation(currPlayerX, currPlayerY + 1);	
					}	
					
				} else {
					// do nothing
				}	
				
			} else if(moveDirection.equalsIgnoreCase(MOVE_WEST)) {
				
				// Check if movement is in bounds - if so move, otherwise stay hold.
				if(currPlayerX - MOVEAMOUNT >= 0) {
					
					// check if movement is valid e.g. blocked by obstacles
					if (currMap.checkTraversable(currPlayerX - 1, currPlayerY)) {
						
						// if true. Update movement.
						player.setLocation(currPlayerX - 1, currPlayerY);	
					}	
					
				} else {
					// do nothing
				}	
					
			} else if(moveDirection.equalsIgnoreCase(ABORT_WORLD)) {
				
				// if enter home, abort world, return to main menu
				System.out.println("Returning home...");
				System.out.println();
				return;
					
			} else {
				
				// Invalid command encountered, do nothing
				
			}
			
			// update player location value as we have updated above
			currPlayerX = player.getLocation()[0];
			currPlayerY = player.getLocation()[1];
			
			
			
			// 4. Item interaction based on player's X&Y
			ArrayList<Item> itemsToRemove = new ArrayList<Item>();
			
			// iterating each entities, if reached (in same cell) then interact
			for (Item aItem : this.currMap.getItems()) {
				if (currPlayerX == aItem.getLocation()[0] && currPlayerY == aItem.getLocation()[1]) {
					aItem.interact(this.player);
					itemsToRemove.add(aItem);
					
					if (aItem.getName().equals("WarpStone")) {
						return;
					}
				}
			}
			
			// after iteration, remove used items from items set
			for (Item aItem : itemsToRemove) {
				this.currMap.removeItem(aItem);
			}

			
			// 5. combat based on player and monster's location
			ArrayList<Monster> monstersToRemove = new ArrayList<Monster>();
			
			// iterating each monster, if reached, then combat
			for (Monster aMonster : this.currMap.getMonsters()) {
				if (currPlayerX == aMonster.getLocation()[0] && currPlayerY == aMonster.getLocation()[1]) {
					// Start combat
					System.out.printf("%s encountered a %s!\n", this.player.getName(), aMonster.getName());
					System.out.println();
					int result = combat(this.player, aMonster);
					
					// if player lose, return to main menu
					if (result == -1) {
						return;
					} else {
						// monster defeated, to be removed
						monstersToRemove.add(aMonster);
					}
				}
			}
			
			// after combat, remove dead monster
			for (Monster aMonster : monstersToRemove) {
				this.currMap.removeMonster(aMonster);
			}

		}
		
		

	}
	
	/*
	 *  Combat loop, fight until one participant health drop below zero. 
	 */
	 
	private int combat(Player aPlayer, Monster aMonster) {
		
		boolean someoneWin = false;
		while (someoneWin == false) {
			
			// Display combat parties info
			System.out.printf("%s %d/%d | %s %d/%d\n", aPlayer.getName(), aPlayer.getCurrHP(), aPlayer.getMaxHP(),
								aMonster.getName(), aMonster.getCurrHP(), aMonster.getMaxHP());
			
			// Player first attach monster
			System.out.printf("%s attacks %s for %d damage.\n", aPlayer.getName(), aMonster.getName(), aPlayer.getDamage());
								aMonster.setCurrHP(aMonster.getCurrHP() - aPlayer.getDamage());
			
			// Check if monster died to prevent player taking extra damage					
			if (aMonster.getCurrHP() <= 0) {
				someoneWin = true;
				break;
			}
			
			// Monster then attack player
			System.out.printf("%s attacks %s for %d damage.\n", aMonster.getName(), aPlayer.getName(), aMonster.getDamage());
			aPlayer.setCurrHP(aPlayer.getCurrHP() - aMonster.getDamage());
			
			
			// Check if player died
			if (aPlayer.getCurrHP() <= 0) {
				someoneWin = true;
				break;
			}
			
			System.out.println();
		}
		
		/* 
		 * Decide winner
		 */
		
		// if monster HP less than 0, monster lose
		if (aMonster.getCurrHP() <= 0) {
			System.out.printf("%s wins!\n", aPlayer.getName());
			System.out.println();
			return 1;
		}
		
		// if player HP less than 0, player lose
		else if (aPlayer.getCurrHP() <= 0) {
			System.out.printf("%s wins!\n", aMonster.getName());
			System.out.println();
			return -1;
		}
		System.out.println();
		return 0;
	}
	
}
