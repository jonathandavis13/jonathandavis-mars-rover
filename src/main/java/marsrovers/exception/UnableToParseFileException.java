package marsrovers.exception;

public class UnableToParseFileException extends RuntimeException{

    private final static String MESSAGE = "Unable to parse file : ";

    public UnableToParseFileException(String reasonCannotParse) {
        super(MESSAGE+reasonCannotParse);
    }
}
