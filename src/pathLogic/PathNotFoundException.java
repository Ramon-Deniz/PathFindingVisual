package pathLogic;

public class PathNotFoundException extends Exception{
	public PathNotFoundException() {
		super("A path is not available");
	}
}
