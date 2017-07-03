package bzh.zomzog.zeshop.product.exception;

/**
 * Created by Zomzog on 30/06/2017.
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(final String message) {
        super(message);
    }

    public StorageFileNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}