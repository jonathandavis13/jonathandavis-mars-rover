package marsrovers.exception;

public class RoverOutOfBoundsException extends RuntimeException {

    private final static String MESSAGE = "The Instructions for the Rover will cause it to fall off the Plateau";

    public RoverOutOfBoundsException(){
        super(MESSAGE);
    }
}
