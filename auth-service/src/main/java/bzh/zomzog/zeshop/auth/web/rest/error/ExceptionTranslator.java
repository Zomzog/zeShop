package bzh.zomzog.zeshop.auth.web.rest.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bzh.zomzog.zeshop.auth.exception.LoginAlreadyInUseException;
import bzh.zomzog.zeshop.web.rest.errors.CommonExceptionTranslator;
import bzh.zomzog.zeshop.web.rest.errors.ErrorConstants;
import bzh.zomzog.zeshop.web.rest.errors.ErrorVM;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator extends CommonExceptionTranslator {
    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(LoginAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processLoginAlreadyInUseException(final LoginAlreadyInUseException ex) {
        this.log.info("Login already in use exception", ex);
        return new ErrorVM(ErrorConstants.ERR_BAD_PARAMETER_ERROR, ex.getMessage());
    }
}
