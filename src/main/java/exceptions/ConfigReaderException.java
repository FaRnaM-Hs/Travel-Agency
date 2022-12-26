package exceptions;

import java.io.IOException;

public class ConfigReaderException extends RuntimeException {
    public ConfigReaderException(IOException e) {
        super(e);
    }
}
