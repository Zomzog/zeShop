package bzh.zomzog.zeshop.cart.web.rest.error;

import bzh.zomzog.zeshop.common.web.rest.errors.CommonExceptionTranslator;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator extends CommonExceptionTranslator {

}
