package bzh.zomzog.zeshop.web.rest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "zeShopApp";

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(final String message, final String param) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-zeShopApp-alert", message);
        headers.add("X-zeShopApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(final String entityName, final String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(final String entityName, final String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(final String entityName, final String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(final String entityName, final String errorKey,
            final String defaultMessage) {
        log.error("Entity creation failed, {}", defaultMessage);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-zeShopApp-error", "error." + errorKey);
        headers.add("X-zeShopApp-params", entityName);
        return headers;
    }
}