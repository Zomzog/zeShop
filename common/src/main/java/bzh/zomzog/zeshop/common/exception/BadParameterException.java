package bzh.zomzog.zeshop.common.exception;

/**
 * Exception for bad parameter
 *
 * @author Zomzog
 */
public class BadParameterException extends FunctionalException {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 3847435050491648443L;

    private final String objectName;
    private final String fieldName;
    private final String value;

    public BadParameterException(final String objectName, final String fieldName, final String value) {
        super();
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.value = value;
    }

    public BadParameterException(final String objectName, final String fieldName, final String value,
                                 final String message) {
        super(message);
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.value = value;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return this.objectName;
    }

}
