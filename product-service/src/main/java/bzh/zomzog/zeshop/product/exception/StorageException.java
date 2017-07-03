package bzh.zomzog.zeshop.product.exception;

/**
 * Created by Zomzog on 30/06/2017.
 */
public class StorageException extends Exception {
    public StorageException() {
    }

    public StorageException(final String message) {
        super(message);
    }

    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public StorageException(final Throwable cause) {
        super(cause);
    }

    public StorageException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
