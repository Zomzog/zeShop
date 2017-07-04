package bzh.zomzog.zeshop.cart.service.client;

import bzh.zomzog.zeshop.cart.domain.product.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Zomzog on 04/07/2017.
 */
@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    Product getProduct(@PathVariable("id") long id);
}
