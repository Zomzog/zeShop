package bzh.zomzog.zeshop.exception;

import feign.FeignException;

/**
 * Created by Zomzog on 04/07/2017.
 */
public class MockFeignException extends FeignException {

    public MockFeignException(final int status, final String message) {
        super(status, message);
    }
}
