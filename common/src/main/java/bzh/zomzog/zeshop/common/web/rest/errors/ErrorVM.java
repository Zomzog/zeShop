package bzh.zomzog.zeshop.common.web.rest.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * View Model for transferring error message with a list of field errors.
 */
public class ErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    private List<FieldErrorVM> fieldErrors;

    public ErrorVM(final String message) {
        this(message, null);
    }

    public ErrorVM(final String message, final String description) {
        this.message = message;
        this.description = description;
    }

    public ErrorVM(final String message, final String description, final List<FieldErrorVM> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(final String objectName, final String field, final String message) {
        if (this.fieldErrors == null) {
            this.fieldErrors = new ArrayList<>();
        }
        this.fieldErrors.add(new FieldErrorVM(objectName, field, message));
    }

    public String getMessage() {
        return this.message;
    }

    public String getDescription() {
        return this.description;
    }

    public List<FieldErrorVM> getFieldErrors() {
        return this.fieldErrors;
    }
}
