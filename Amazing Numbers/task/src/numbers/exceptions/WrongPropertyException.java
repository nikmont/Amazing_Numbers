package numbers.exceptions;

import java.util.Arrays;

public class WrongPropertyException extends Exception {

    public WrongPropertyException(Object[] message) {

        super("The " + (message.length == 1 ? "property " : "properties ") + Arrays.toString(message).toUpperCase() + (message.length == 1 ? " is " : " are ") + "wrong.\n" +
                "Available properties:\n" +
                "[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
    }
}
