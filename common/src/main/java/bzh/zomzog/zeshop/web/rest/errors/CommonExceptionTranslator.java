package bzh.zomzog.zeshop.web.rest.errors;

import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.exception.FunctionalException;
import bzh.zomzog.zeshop.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
public class CommonExceptionTranslator {
    private final Logger log = LoggerFactory.getLogger(CommonExceptionTranslator.class);

    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorVM processConcurrencyError(final ConcurrencyFailureException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_CONCURRENCY_FAILURE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processValidationError(final MethodArgumentNotValidException ex) {
        this.log.error(ex.getClass().getName(), ex);
        final BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorVM processParameterizedValidationError(final CustomParameterizedException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return ex.getErrorVM();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorVM processAccessDeniedException(final AccessDeniedException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_ACCESS_DENIED, ex.getMessage());
    }

    private ErrorVM processFieldErrors(final List<FieldError> fieldErrors) {
        final ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);

        for (final FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }

        return dto;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorVM processMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, ex.getMessage());
    }

    @ExceptionHandler(BadParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorVM badParameterException(final BadParameterException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_BAD_PARAMETER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorVM notFoundException(final NotFoundException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(FunctionalException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorVM notFoundException(final FunctionalException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_FUNCTIONNAL_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVM> processRuntimeException(final Exception ex) {
        this.log.error(ex.getClass().getName(), ex);
        final BodyBuilder builder;
        final ErrorVM errorVM;
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorVM = new ErrorVM("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorVM = new ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return builder.body(errorVM);
    }
}
