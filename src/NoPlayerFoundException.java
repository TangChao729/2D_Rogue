
public class NoPlayerFoundException extends Exception {
	
	public NoPlayerFoundException() {
		super("No player data found");
	}
	
	public NoPlayerFoundException(String message) {
		super(message);
	}
}
