package kakaopay.error.exception;

public class NotExistUserInvestException extends RuntimeException {

    public static final String errorCode = "not.exist.X-USER-ID";

    public NotExistUserInvestException(String message) {
        super(message);
    }

    public NotExistUserInvestException(String message, Throwable cause) {
        super(message, cause);
    }

}
