package prbetter.mapper;

public final class JsonDeserializeException extends RuntimeException {
    private static final String MESSAGE_PREFIX = "[ERROR] ";

    public JsonDeserializeException(String message, Throwable cause) {
        super(MESSAGE_PREFIX + message, cause);
    }
}
