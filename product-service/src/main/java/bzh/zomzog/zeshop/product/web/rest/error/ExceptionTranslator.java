package bzh.zomzog.zeshop.product.web.rest.error;

import org.springframework.web.bind.annotation.ControllerAdvice;

import bzh.zomzog.zeshop.web.rest.errors.CommonExceptionTranslator;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator extends CommonExceptionTranslator {

}
