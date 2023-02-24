import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * COMP90041 Assignment 2 - Rogue Expanded
 * 
 * Welcome to Rogue Expanded, the advanced version of the base game you implemented in Assignment 1. 
 * In this assignment, we will build on our existing game to add more features to make it a more fully-featured game.

 * 
 * @author: Taylor Tang 1323782 chaojunt@student.unimelb.edu.au
 *
 */

public class SaveLoad {
	
	/*
	 * Variables to read from file
	 */
	private int mapWidth, mapHeight;
	private String[] mapTerrain;
	private Map aMap;
	private Scanner inputStream;
	
	
	// To delete when upload to ED
	String prefix = "/Users/taylortang/eclipse-workspace/Rogue_Assignment2/src/";
	String afterfix = ".dat";
	
	
	// default constructor
	public SaveLoad() {
		
	}

	/*
	 * save & load player info from player.dat
	 * structure example:
	 * Bilbo 1
	 */
	public void savePlayer(Player aPlayer) throws NoPlayerDataToSaveException{
		
		// Always save in player.dat
		String filename = "player.dat";
		
		try {
			
			if (aPlayer == null) {
				
				// prevent system from crash
				throw new NoPlayerDataToSaveException();
				
			}
			
			// start writing to file
			FileOutputStream fos = new FileOutputStream(prefix + filename);
			PrintWriter pw = new PrintWriter(fos);
			pw.print(aPlayer.getName());
			pw.print(" ");
			pw.print(aPlayer.getLevel());
			
			// close and report
			pw.close();
			System.out.println("Player data saved");
			
		} catch (FileNotFoundException fnfe) {
			
			System.out.println("Failed to save player into file.");
			
		}
	}
	
	/*
	 * load a player from file, create new player
	 * Note load file only contains Name & level. -- location info in map file
	 */
	public Player loadPlayer() throws NoPlayerFoundException{
		
		// new player holder
		Player newPlayer = null;
		
		// always read from player.dat
		String filename = "player.dat";
		
		try {
			
			inputStream = new Scanner(new FileInputStream(prefix + filename));
			String aPlayerInfo = inputStream.nextLine();
			
			// handle player creation in helper methods
			newPlayer = parsePlayerInfo(aPlayerInfo);

			// close
			inputStream.close();

		} catch (FileNotFoundException fnfe) {
			
            System.err.println("Failed to load player from file.");

		}
		
		// As requested by assignment 2, throw player no found exception here
		if (newPlayer != null) {
			
			return newPlayer;
		
		} else {
			
			throw new NoPlayerFoundException();
			
		}
	}
	
	/* To read from simple.dat file, then parse each information
	 * parse mapHeight, mapWidth & mapTerrain to map
	 * parse player info to player
	 * parse monster to monster
	 * parse entities to entities
	 * 
	 * This is used only for "START xxx.dat" mode
	 * This method is called in GameEngine.
	 */
	public Map loadWorld(Player aPlayer, String filename) throws GameLevelNotFoundException {
		
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		ArrayList<Item> items = new ArrayList<Item>();

		try {
			
			// throw exception file cannot be find
			String fullFileName = prefix + filename + afterfix;
			this.inputStream = new Scanner(new FileInputStream(fullFileName));
			
				
			// read first two int, mapWidth & height
			this.mapWidth = inputStream.nextInt();
			this.mapHeight = inputStream.nextInt();
			inputStream.nextLine();
			
			// read next few line, mapTerrain
			this.mapTerrain = new String[this.mapHeight];
			for (int i = 0; i < this.mapHeight; i++) {
				this.mapTerrain[i] = this.inputStream.nextLine();
			}
			
			// read next lines for entities
			String nextString = null;
			
			while (this.inputStream.hasNext()) {	
				
				// read nextline, player
				nextString = this.inputStream.nextLine();
				
				// distribute entities to each slot
				if (nextString.matches("player(.*)")) {
					
					int[] playerLocation = parsePlayerLocation(nextString);
					aPlayer.setLocation(playerLocation[0], playerLocation[1]);
					
				} else if (nextString.matches("monster(.*)")) {
					
					Monster aMonster = parseMonster(nextString);
					monsters.add(aMonster);
					
				} else if (nextString.matches("item(.*)")) {
					
					Item aItem = parseItem(nextString);
					items.add(aItem);
					
				}
				
			}
			
			// construct a new map with all elements read above
			this.aMap = new Map(mapWidth, mapHeight, mapTerrain, aPlayer, monsters, items);
			this.inputStream.close();
			
		} catch (FileNotFoundException fnfe) {
			
			throw new GameLevelNotFoundException();
			
		}
		
		return this.aMap;
		
	}

	// below are helper methods.
	
	/*
	 *  to used in loadPlayer(), different to parsing others
	 */
	private Player parsePlayerInfo(String aPlayerInfo) {
		
		String[] infos = aPlayerInfo.split("\\s+");
		String aPlayerName = infos[0];
		Player newPlayer = new Player(aPlayerName);
		
		int aPlayerLevel = Integer.parseInt(infos[1]);
		newPlayer.setLevel(aPlayerLevel);

		return newPlayer;
		
	}
	
	
	/* to used in loadWorld()
	 * extract player info to update player object
	 */ 
	private int[] parsePlayerLocation(String playerInfo) {
		String[] infos = playerInfo.split("\\s+");
		int playerXAxis = Integer.parseInt(infos[1]);
		int playerYAxis = Integer.parseInt(infos[2]);
		int[] playerLocation = {playerXAxis, playerYAxis};
		return playerLocation;
	}
	
	/* to used in loadWorld()
	 * extract monster info to create monster objects
	 */
	private Monster parseMonster(String monsterInfo) {
		
		String[] infos = monsterInfo.split("\\s+");
		String monsterName = infos[3];
		int monsterXAxis = Integer.parseInt(infos[1]);
		int monsterYAxis = Integer.parseInt(infos[2]);
		int monsterHealth = Integer.parseInt(infos[4]);
		int monsterDamage = Integer.parseInt(infos[5]);

		Monster aMonster = new Monster(monsterName, monsterXAxis, monsterYAxis, monsterHealth, monsterDamage);
		return aMonster;
	}
	
	/* to used in loadWorld()
	 * extract monster info to create monster objects
	 */
	private Item parseItem(String itemInfo) {
		String[] infos = itemInfo.split("\\s+");
		int itemXAxis = Integer.parseInt(infos[1]);
		int itemYAxis = Integer.parseInt(infos[2]);
		char itemIcon = infos[3].charAt(0);
		String itemName = null;
		
		if (itemIcon == '+') {
			itemName = "HealingPotion";
		} else  if (itemIcon == '^') {
			itemName = "DamagePerk";
		} else  if (itemIcon == '@') {
			itemName = "WarpStone";
		} else {
			System.out.println("Cannot understand what item is this");
		}


		Item aItem = new Item(itemName, itemXAxis, itemYAxis);
		return aItem;	
	}
}
