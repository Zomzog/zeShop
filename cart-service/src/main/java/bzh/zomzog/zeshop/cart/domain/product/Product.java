package bzh.zomzog.zeshop.cart.domain.product;

/**
 * Created by Zomzog on 04/07/2017.
 */
public class Product {

    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Product id(final Long id) {
        this.id = id;
        return this;
    }
}
