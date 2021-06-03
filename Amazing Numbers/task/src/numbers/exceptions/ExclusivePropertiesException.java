package numbers.exceptions;

public class ExclusivePropertiesException extends Exception{

    public ExclusivePropertiesException(String message) {
        super(message.toUpperCase());
    }
}
