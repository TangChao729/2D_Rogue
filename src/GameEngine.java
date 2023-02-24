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


/*
 *  GameEngine class, provide program initiation, menu construction and interaction, and objects set up.
 */

public class GameEngine {
	private Player player;
	private Monster monster;
	private SaveLoad saveLoad;
	public static final Scanner keyboard = new Scanner(System.in);
	
	final static String COMMANDS = "commands";
	final static String HELP = "help";
	final static String PLAYER = "player";
	final static String MONSTER = "monster";
	final static String START = "start";
	final static String EXIT = "exit";
	final static String SAVE = "save";
	final static String LOAD = "load";
	
	
	/*
	 *  main args to initiate program
	 */

	public static void main(String[] args) {
		// Creates an instance of the game engine.
		GameEngine gameEngine = new GameEngine();
		
		// Runs the main game loop.
		gameEngine.runGameLoop();
				
	}
	
	/*
	 *  Logic for running the main game loop.
	 */

	private void runGameLoop() {
		
		// Initialed save&load module
		this.saveLoad = new SaveLoad();

		// Start game with menu;
		mainMenu();

	}
	
	/*
	 *  Displays the title text.
	 */

	public void displayTitleText() {
		// display current game overall status
		
		// check if local player exist. If not, display [None]
		String playerToDisplay;
		if (this.player != null) {
			playerToDisplay = player.getName() + " " + player.getCurrHP() + "/" + player.getMaxHP();
		} else {
			playerToDisplay = "[None]";
		}
		
		// check if local monster exist. If not, display [None]
		String monsterToDisplay;
		if (this.monster != null) {
			monsterToDisplay = monster.getName() + " " + monster.getCurrHP() + "/" + monster.getMaxHP();
		} else {
			monsterToDisplay = "[None]";
		}
			
		// Logo, status and description text code (regex)
		String titleText = " ____                        \n" + 
				"|  _ \\ ___   __ _ _   _  ___ \n" + 
				"| |_) / _ \\ / _` | | | |/ _ \\\n" + 
				"|  _ < (_) | (_| | |_| |  __/\n" + 
				"|_| \\_\\___/ \\__, |\\__,_|\\___|\n" + 
				"COMP90041   |___/ Assignment ";
		
		System.out.println(titleText);
		System.out.println("");
		System.out.printf("Player: %s  | Monster: %s\n", playerToDisplay, monsterToDisplay);
		System.out.println("");
		System.out.println("Please enter a command to continue.");
		System.out.println("Type 'help' to learn how to get started.");
		System.out.println();
		
		
	}
	
	
	/*
	 *  main menu method and valid inputs
	 */

	public void mainMenu() {
		
		displayTitleText();
		
		boolean exitMainMenu = false;
		
		while (exitMainMenu != true) {
			// Print a user prompt.
			System.out.print("> ");

			// parse input to use
			String totalInput = keyboard.nextLine().toLowerCase();
			String[] restOfLine = splitCommand(totalInput);
			String commandInput = restOfLine[0];

			//Deciding actions based on user input
			switch (commandInput) {
			
				case COMMANDS:
					
					commandsMenu();
					break;
					
				case HELP:
					
					helpMenu();
					break;
					
				case PLAYER:
					
					playerMenu();
					break;
					
				case MONSTER:
					
					monsterMenu();
					break;
					
				case SAVE:
					
					try {
						
						saveLoad.savePlayer(this.player);
						
					} catch (NoPlayerDataToSaveException npdtse) {
						
						System.out.println(npdtse.getMessage());
						
					}
					
					break;
					
				case LOAD:
					
					try {
						
						this.player = saveLoad.loadPlayer();
						
					} catch (NoPlayerFoundException npfe) {
						
						System.out.println(npfe.getMessage());
						
					}
					
					break;
					
				case START: //check command see if want to start to loading file
					
					// AS1 version				
					if (restOfLine.length == 1) {
						
						// check if player name exists
						if (!playerCheck(this.player)) {
							
							returnToMenu();
							
						} 
						
						// check if monster exists
						else if (!monsterCheck(this.monster)) {
							
							returnToMenu();
							
						} 
						
						// start game
						else {
							
							startAS1Game(this.player, this.monster);
							returnToMenu();
							
						}					

					}
					
					// if input "start + xxx" AS2 version
					else if (restOfLine.length == 2) {
						
						//check if player name exists, and if new input valid
						if (!playerCheck(this.player)) {
							
							returnToMenu();
							
						} 
						
						// start game
						else {
							
							// second letter from "start + xxx" is the filename
							String fileName = restOfLine[1];
							
							startAS2Game(this.player, fileName);
												
							returnToMenu();
							
						}
					}
					
					break;
				
				// ending condition	
				case EXIT:
					
					System.out.println("Thank you for playing Rogue!");
					exitMainMenu = true;
					break;
				
				// invalid input feedback	
				default:
					// do nothing
					// System.out.println("Invalid input");
					break;
			}

		}
	}
	
	/*
	 * Help method, to start game with AS1 version
	 */
	
	public void startAS1Game(Player aPlayer, Monster aMonster) {
		
		// start the game, set fullHP
		this.player.setCurrHP(this.player.getMaxHP());
		this.monster.setCurrHP(this.monster.getMaxHP());
		
		//invoke new world
		World world = new World(this.player, this.monster);
		
		//start explore
		world.explore();
		
	}
	
	/*
	 * Help method, to start game with AS2 version
	 */
	
	public void startAS2Game(Player aPlayer, String fileName) {
		
		// start the game, set fullHP
		this.player.setCurrHP(this.player.getMaxHP());
		
		// load world file, start new version
		try {
			
			Map currMap = saveLoad.loadWorld(this.player, fileName);
			
			//invoke new world
			World world = new World(currMap);
			
			//start explore
			world.explore();
			
		} catch (GameLevelNotFoundException glnfe) {
			
			System.out.println(glnfe.getMessage());
			System.out.println();
			
		}

		
	}
	
	/*
	 * Help method to check if local player exist
	 */
	
	public boolean playerCheck(Player aPlayer) {
		
		if (aPlayer == null) {
			
			System.out.println("No player found, please create a player with 'player' first.");
			System.out.println();
			return false;
			
		} 
		
		return true;
		
	}
	
	/*
	 * Help method to check if local monster exist
	 */
	
	public boolean monsterCheck(Monster aMonster) {
		
		if (aMonster == null) {
			
			System.out.println("No monster found, please create a monster with 'monster' first.");
			System.out.println();
			return false;
			
		} 
		
		return true;
		
	}
	
	
	/*
	 *  Help menu method, display helping information
	 */

	public void helpMenu() {
		
		System.out.println("Type 'commands' to list all available commands");
		System.out.println("Type 'start' to start a new game");
		System.out.println("Create a character, battle monsters, and find treasure!");
		System.out.println();
		
	}
	
	
	/*
	 *  Command menu method, display valid commands
	 */

	public void commandsMenu() {
		
		System.out.println("help");
		System.out.println("player");
		System.out.println("monster");
		System.out.println("start");
		System.out.println("save");
		System.out.println("load");
		System.out.println("exit");
		System.out.println();
		
	}
	
	
	/*
	 *  Player menu method, provide or display player information
	 */

	public void playerMenu() {
		
		if (this.player != null) {
			
			//display player info
			System.out.printf("%s (Lv. %d)\n", player.getName(), player.getLevel());
			System.out.printf("Damage: %d\n", player.getDamage());
			System.out.printf("Health: %d/%d\n", player.getCurrHP(), player.getMaxHP());
			System.out.println();
			
		} else {
			
			// set player info
			System.out.println("What is your character's name?");		
			String playerName = keyboard.next();
			keyboard.nextLine();
			
			while (playerName.length() == 0) {
				
				System.out.printf("Player's name should be at least 1 character");
				System.out.printf("What is your character's name");
				keyboard.nextLine();
				
			}
			
			this.player = new Player(playerName);
			System.out.printf("Player '%s' created.\n", playerName);
			System.out.println();

		}
		returnToMenu();
		
	}
	
	
	/*
	 *  Monster menu method, provide to set monster information
	 *  If monster exist, reset monster details
	 */

	public void monsterMenu() {
		
		System.out.print("Monster name: ");
		String monsterName = keyboard.next();
		keyboard.nextLine();
		
		while (monsterName.length() == 0) {
			
			System.out.printf("Monster's name should be at least 1 character");
			System.out.printf("Monster name:");
			keyboard.nextLine();
			
		}
		
		System.out.print("Monster health: ");
		int monsterMaxHP = keyboard.nextInt();
		keyboard.nextLine();
		
		System.out.print("Monster damage: ");
		int monsterDamage = keyboard.nextInt();
		keyboard.nextLine();
		
		this.monster = new Monster(monsterName, monsterMaxHP, monsterDamage);
		System.out.printf("Monster '%s' created.\n", monster.getName());
		System.out.println();
		
		returnToMenu();
		
	}
	
	
	/*
	 *  Helper return to menu method, require user confirmation to return to main menu
	 */
	 
	private void returnToMenu() {
		
		System.out.println("(Press enter key to return to main menu)");
		String returnToMenu = keyboard.nextLine();

		// sanity check	
		if (returnToMenu.length() != -1) {
			
			displayTitleText();
			
		} else {
			
			System.out.println("Invalid input");
			
		}
		
	}
	
	
	/*
	 * Helper method to split command, in order to distinguish AS1 version
	 * and AS2 version (start simple.dat)
	 */
	private String[] splitCommand(String command) {
		
		String[] results = command.split(" ");
		return results;
		
	}
	
}
