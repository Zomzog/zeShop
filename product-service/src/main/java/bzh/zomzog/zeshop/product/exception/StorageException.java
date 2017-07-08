package bzh.zomzog.zeshop.product.exception;

/**
 * Created by Zomzog on 30/06/2017.
 */
public class StorageException extends Exception {

    public StorageException(final String message) {
        super(message);
    }

    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
