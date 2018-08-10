package marsrovers.exception;

public class InvalidDirectionException extends RuntimeException {

    private final static String  MESSAGE = "Tried to turn a rover in a direction that was not NORTH,EAST,SOUTH or WEST";

    public InvalidDirectionException() {
        super(MESSAGE);
    }


}
