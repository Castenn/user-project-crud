package ua.casten.user.project.exception;

public class NullEntityReferenceException extends RuntimeException {
    public NullEntityReferenceException() {
        super();
    }

    public NullEntityReferenceException(String message) {
        super(message);
    }

    public NullEntityReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullEntityReferenceException(Throwable cause) {
        super(cause);
    }
}
