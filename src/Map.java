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
 * Map and Drawing class
 * Containing detailed map information include:
 * 	- map width & height
 * 	- map terrain in char[][]
 * 	- player in this map
 * 	- all monsters in this map
 * 	- all items in this map
 * 
 * Drawing containing:
 * 	- drawDefault map with "." background
 * 	- load map terrain from file
 * 	- draw new map based map terrain
 * 	- check if map cell is travel-able
 */


public class Map {
	// Default map size
	final static int DEF_MAP_WIDTH = 6;
	final static int DEF_MAP_HEIGHT = 4;
	
	// Customizable map size
	private int mapWidth;
	private int mapHeight;
	
	// detailed map terrain save in char[][] with each char as a cell
	private char[][] currMap;
	
	// ALL entities
	private Player player;
	private ArrayList<Monster> monsters;
	private ArrayList<Item> items;
	
	// Groupable for iteration
	private ArrayList<Entity> entities;
	
	// terrain representation
	final static char GROUND = '.';
	final static private char MONTAIN = '#';
	final static private char WATER = '~';

	
	
	// constructor for default map in AS1 version
	public Map(Player aPlayer, Monster aMonster) {

		setMapWidth(DEF_MAP_WIDTH);
		setMapHeight(DEF_MAP_HEIGHT);
		loadOldMap();
		
		this.player = aPlayer;
		
		// prevent null pointer exception
		this.monsters = new ArrayList<Monster>();
		this.items = new ArrayList<Item>();

		this.monsters.add(aMonster);
		
		
	}
	
	// constructor for loaded map in AS2 version
	public Map(int mapWidth, int mapHeight, String[] mapTerrain, Player aPlayer, ArrayList<Monster> monsters, ArrayList<Item> items) {
		setMapWidth(mapWidth);
		setMapHeight(mapHeight);
		loadNewMap(mapTerrain);
		
		this.player = aPlayer;
		this.monsters = monsters;
		this.items = items;
				
	}
	
	/*
	 * loadNewMap terrain based on loaded file
	 * terrain include mountain#, water~ and plain field..
	 */
	
	private void loadNewMap(String[] mapTerrain) {
		
		int aMapWidth = mapTerrain[0].length();
		int aMapHeight = mapTerrain.length;
		
		char[][] aMapInCharList= new char[aMapHeight][aMapWidth];
		
		for (int y = 0; y < aMapHeight; y++) {
			for (int x = 0; x < aMapWidth; x++) {
				aMapInCharList[y][x] = mapTerrain[y].charAt(x);
			}
		}
		this.currMap = aMapInCharList;
		
	}
	
	/*
	 * loadOldMap terrain with "..." 
	 * and default width and height
	 */
	
	private void loadOldMap() {
		
		char[][] aMapInCharList= new char[Map.DEF_MAP_HEIGHT][Map.DEF_MAP_WIDTH];
		
		for (int y = 0; y < Map.DEF_MAP_HEIGHT; y++) {
			for (int x = 0; x < Map.DEF_MAP_WIDTH; x++) {
				aMapInCharList[y][x] = '.';
			}
		}
		this.currMap = aMapInCharList;
		
	}
	
	
	/*
	 * draw a map based on currMap variable
	 * including all entities in the map
	 */
	
	public void drawMap() {
		
		for (int y = 0; y < this.mapHeight; y++) {
			for (int x = 0; x < this.mapWidth; x++) {
				// Output the appropriate symbol for each cell.
				boolean isPixelDrawn = false;
				
				// Group all entities for easy iteration
				groupAllEntities();
				
				for (Entity entity : entities) {
					isPixelDrawn = drawCell(x, y, entity);

					// Once something is drawn, no longer need to draw anything else.
					if (isPixelDrawn) {
						break;
					}

				}
				
				// If nothing was drawn at that position, draw the empty cell instead.
				if (!isPixelDrawn) {
					System.out.print(this.currMap[y][x]);
				}
			}
			// Create a newline at the end of each row. of the map we render.
			System.out.println();
		}
		
		// add extra line at end of map
		System.out.println();
	}
	
	/*
	 * Help method to used in drawMap()
	 * Check if current cell is occupied by any entity
	 * draw a single cell based on entities' mapCell
	 */
	
	private boolean drawCell(int xAxis, int yAxis, Entity aEntity) {
		
		// check if cell is occupied by this entity
		if (xAxis == aEntity.getLocation()[0] && yAxis == aEntity.getLocation()[1]) {
			
			// draw entity's mapCell
			System.out.print(aEntity.getMapCell());
			return true;
		}
		return false;
		
	}
	
	/*
	 * Return boolean value of if the input cell index is traversable or not.
	 * Used in check player move validation and monster move calculation.
	 */
	
	public boolean checkTraversable(int xAxis, int yAxis) {
		
		boolean traversable = false;
		if (this.currMap[yAxis][xAxis] == GROUND) {
			traversable = true;
		} else if (this.currMap[yAxis][xAxis] == MONTAIN) {
			traversable = false;
		} else if (this.currMap[yAxis][xAxis] == WATER) {
			traversable = false;
		}
		return traversable;
		
	}
	
	/*
	 * Group all entities, help method.
	 */
	private void groupAllEntities() {
		
		// create new ArrayList to prevent old entites stay in new game
		ArrayList<Entity> allEntities = new ArrayList<Entity>();
		
		// add the player
		allEntities.add(this.player);
		
		// add all monsters
		for (Monster m : this.monsters) {
			allEntities.add(m);
		}
		
		// add all items
		for (Item i : this.items) {
			allEntities.add(i);
		}
		
		// update variable
		this.entities = allEntities;
		
	}
	
	
	/* 
	 * setters & getters
	 */
	
	private void setMapWidth(int mapWidth) {
		
		this.mapWidth = mapWidth;
		
	}
	
	
	private void setMapHeight(int mapHeight) {
		
		this.mapHeight = mapHeight;
		
	}
	
	
	public int getMapHeight() {
		
		return this.mapHeight;
		
	}
	
	
	public int getMapWidth() {
		
		return this.mapWidth;
		
	}
	
	
	public Player getPlayer() {
		
		return this.player;
		
	}
	
	
	public ArrayList<Monster>  getMonsters() {
		
		return this.monsters;
		
	}
	
	
	public ArrayList<Item> getItems() {
		
		return this.items;
		
	}
	
	
	/*
	 *  Remover, used for dead monsters and used items
	 */
	
	public void removeMonster(Monster aMonster) {
		
		this.monsters.remove(aMonster);

	}
	
	
	public void removeItem(Item aItem) {
		
		this.items.remove(aItem);
	
	}
}
