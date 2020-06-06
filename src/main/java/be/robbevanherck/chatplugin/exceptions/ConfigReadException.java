package be.robbevanherck.chatplugin.exceptions;

import java.io.IOException;

/**
 * Exception that occurs during config reading
 */
public class ConfigReadException extends RuntimeException {
    /**
     * Exception that occurs during config reading
     * @param message A descriptive message
     * @param e the nested exception
     */
    public ConfigReadException(String message, IOException e) {
        super(message, e);
    }
}
