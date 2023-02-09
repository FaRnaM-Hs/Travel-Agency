package exceptions;

import java.io.IOException;

public class PropertyException extends RuntimeException {
    public PropertyException(IOException e) {
        super(e);
    }
}
