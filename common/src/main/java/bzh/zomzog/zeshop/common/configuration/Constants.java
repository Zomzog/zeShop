package bzh.zomzog.zeshop.common.configuration;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    public static final String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }
}
