package bzh.zomzog.zeshop.auth.web.rest.error;

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

    @ExceptionHandler(LoginAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processLoginAlreadyInUseException(final LoginAlreadyInUseException ex) {
        return new ErrorVM(ErrorConstants.ERR_BAD_PARAMETER_ERROR, ex.getMessage());
    }
}
