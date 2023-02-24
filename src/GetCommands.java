import java.util.Scanner;

public class GetCommands {
	
	public static final Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	private void runGameLoop() {
		
		// Initialed save&load module
		//this.saveLoad = new SaveLoad();

		// Start game with menu;
		mainMenu();

	}
	
	public void mainMenu() {
		
		//displayTitleText();
		
		//boolean exitMainMenu = false;
		
		//while (exitMainMenu != true) {
			// Print a user prompt.
			//System.out.print("> ");
		while (true) {
			String commandInput = keyboard.nextLine();
			
			//check command
			System.out.println("Command: " + commandInput);
		}
		

	}

}
