package bzh.zomzog.zeshop.auth.exception;

import bzh.zomzog.zeshop.exception.FunctionalException;

/**
 * Exception fro login already in use
 * 
 * @author Zomzog
 *
 */
public class LoginAlreadyInUseException extends FunctionalException {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 5888514456210448785L;

    public LoginAlreadyInUseException() {
        super("login already in use");
    }

    public LoginAlreadyInUseException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LoginAlreadyInUseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LoginAlreadyInUseException(final String message) {
        super(message);
    }

    public LoginAlreadyInUseException(final Throwable cause) {
        super(cause);
    }

}
