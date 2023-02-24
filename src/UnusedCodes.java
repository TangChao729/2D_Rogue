//
//public class UnusedCodes {
//	// default map, this is similar structure to read from dat file
//	private String[] defaultMapInString =  {"...####.....",
//			"....##......",
//			"............",
//			"~~~.........",
//			"~~~~~~~.....",
//			"~~~~~~~~~~~~"};
//	
//	private void loadDefaultMap() {
//		// this is based on map width and height are accord
//		// might need to add safe guard for uneven height and width
//		int deMapWidth = defaultMapInString[0].length();
//		int deMapHeight = defaultMapInString.length;
//		
//		char[][] deMapInCharList= new char[deMapHeight][deMapWidth];
//		
//		for (int y = 0; y < deMapHeight; y++) {
//			for (int x = 0; x < deMapWidth; x++) {
//				deMapInCharList[y][x] = defaultMapInString[y].charAt(x);
//			}
//		}
//		System.out.println("Using default map.");
//		this.currMap = deMapInCharList;
//	}

//	// draw map cell if current iteration cell equals this entities location mark
//	public boolean draw(int xAxis, int yAxis) {
//		if (xAxis == mapLocation[0] && yAxis == mapLocation[1]) {
//			drawMapCell();
//			return true;
//		}
//
//		return false;
//	}
//	
//	/**
//	 * Prints the symbol for the entity.
//	 */
//	public void drawMapCell() {
//		System.out.print(this.getMapCell());
//	}

			// changed from switch cases to if/else to detect variables
			
			// show commands menu
//			if (commandInput.equals(COMMANDS)) {
//				commandsMenu();
//			} 
//			
//			// show help menu
//			else if (commandInput.equals(HELP)) {
//				helpMenu();
//			} 
//			
//			// show player menu
//			else if (commandInput.equals(PLAYER)) {
//				playerMenu();
//			} 
//			
//			// show monster menu
//			else if (commandInput.equals(MONSTER)) {
//				monsterMenu();
//			} 
//			
//			// start game on condition
//			// if start with no parameters, start AS 1 version
//			// if start with file name parameters, start AS 2 version
//			else if (commandInput.equals(START)) {
//				
//				// AS1 version				
//				if (restOfLine.length == 1) {
//					
//					// check if player name exists
//					if (!playerCheck(this.player)) {
//						returnToMenu();
//					} 
//					
//					// check if monster exists
//					else if (!monsterCheck(this.monster)) {
//						returnToMenu();
//					} 
//					
//					else {
//						startAS1Game(this.player, this.monster);
//						returnToMenu();
//					}					
//
//				}
//				
//				// AS2 version
//				else if (restOfLine.length == 2) {
//					
//					//check if player name exists, and if new input valid
//					if (!playerCheck(this.player)) {
//						returnToMenu();
//					} else {
//						String fileName = restOfLine[1];
//						
//						startAS2Game(this.player, fileName);
//											
//						returnToMenu();
//					}
//				} 
//			} 
//			
//			// save current player info
//			else if (commandInput.equals(SAVE)) {
//				
//				try {
//					
//					saveLoad.savePlayer(this.player);
//					
//				} catch (NoPlayerDataToSaveException npdtse) {
//					
//					System.out.println(npdtse.getMessage());
//					
//				}
//
//			} 
//			
//			// load to current player info
//			else if (commandInput.equals(LOAD)) {
//				
//				try {
//					
//					this.player = saveLoad.loadPlayer();
//					
//				} catch (NoPlayerFoundException npfe) {
//					
//					System.out.println(npfe.getMessage());
//					
//				}
//				
//			} 
//			
//			// exit game
//			else if (commandInput.equals(EXIT)) {
//				System.out.println("Thank you for playing Rogue!");
//				exitMainMenu = true;
//			} 
//			
//			// Dealing with invalid inputs
//			else {
//				// do not display anything if invalid input
//			}
//}
