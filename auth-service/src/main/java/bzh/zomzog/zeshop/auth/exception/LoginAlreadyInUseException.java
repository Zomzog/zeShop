package bzh.zomzog.zeshop.auth.exception;

import bzh.zomzog.zeshop.exception.FunctionalException;

/**
 * Exception fro login already in use
 *
 * @author Zomzog
 */
public class LoginAlreadyInUseException extends FunctionalException {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 5888514456210448785L;

    public LoginAlreadyInUseException() {
        super("login already in use");
    }

}
