package dao.database.impl.exceptions;

public class NoSuchFlightException extends RuntimeException {
    public NoSuchFlightException(String message) {
        super(message);
    }
}
