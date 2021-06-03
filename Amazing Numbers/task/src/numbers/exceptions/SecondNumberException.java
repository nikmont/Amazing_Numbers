package numbers.exceptions;

public class SecondNumberException extends Exception {

    public SecondNumberException() {
        super("The second parameter should be a natural number.");
    }
}
