package bzh.zomzog.zeshop.product.web.rest.error;

import bzh.zomzog.zeshop.product.exception.StorageFileNotFoundException;
import bzh.zomzog.zeshop.web.rest.errors.CommonExceptionTranslator;
import bzh.zomzog.zeshop.web.rest.errors.ErrorConstants;
import bzh.zomzog.zeshop.web.rest.errors.ErrorVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator extends CommonExceptionTranslator {
    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(StorageFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorVM processConcurrencyError(final StorageFileNotFoundException ex) {
        this.log.error(ex.getClass().getName(), ex);
        return new ErrorVM(ErrorConstants.ERR_BAD_PARAMETER_ERROR);
    }
}
