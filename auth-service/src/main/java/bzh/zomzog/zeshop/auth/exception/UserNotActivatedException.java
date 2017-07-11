package bzh.zomzog.zeshop.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to
 * authenticate.
 */
public class UserNotActivatedException extends AuthenticationException {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = -8475767498790854097L;

    public UserNotActivatedException(final String message) {
        super(message);
    }
}
