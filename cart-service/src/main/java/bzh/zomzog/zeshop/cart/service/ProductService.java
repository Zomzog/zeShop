package bzh.zomzog.zeshop.cart.service;

import bzh.zomzog.zeshop.cart.domain.product.Product;
import bzh.zomzog.zeshop.cart.service.client.ProductClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Zomzog on 04/07/2017.
 */
@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductClient productClient;

    public ProductService(final ProductClient productClient) {
        this.productClient = productClient;
    }

    public Optional<Product> get(final long productId) {
        try {
            final Product product = this.productClient.getProduct(productId);
            return Optional.ofNullable(product);
        } catch (final FeignException e) {
            if (e.status() == 404) {
                return Optional.empty();
            } else {
                this.log.error("Get product error", e);
                throw e;
            }
        }
    }
}
