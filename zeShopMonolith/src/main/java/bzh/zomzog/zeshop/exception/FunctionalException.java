package bzh.zomzog.zeshop.exception;

public class FunctionalException extends Exception {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 7664517780593149849L;

    public FunctionalException() {
        super();
    }

    public FunctionalException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FunctionalException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FunctionalException(final String message) {
        super(message);
    }

    public FunctionalException(final Throwable cause) {
        super(cause);
    }

}
