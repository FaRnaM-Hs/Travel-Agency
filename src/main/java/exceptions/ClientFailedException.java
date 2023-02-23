package exceptions;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientFailedException extends RuntimeException {

    public ClientFailedException(URISyntaxException e) {
        super(e);
    }

    public ClientFailedException(IOException e) {
        super(e);
    }

    public ClientFailedException(InterruptedException e) {
        super(e);
    }
}