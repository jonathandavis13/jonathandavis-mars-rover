package marsrovers.exception;

public class RoverCollisionException extends RuntimeException {

    private final static String MESSAGE = "The Instructions for the Rover will cause a collision with another rover";

    public RoverCollisionException(){
        super(MESSAGE);
    }
}
