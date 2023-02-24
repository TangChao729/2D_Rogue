
public class NoPlayerDataToSaveException extends Exception{
	
	public NoPlayerDataToSaveException() {
		super("No player data to save.");
	}
	
	public NoPlayerDataToSaveException(String message) {
		super(message);
	}
	
}

