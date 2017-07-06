package bzh.zomzog.zeshop.cart.domain.product;

import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zomzog on 04/07/2017.
 */
@ToString
public class Product {

    private Long id;

    private final Set<ProductCustomizationField> customizationFields = new HashSet<>();

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

    public Set<ProductCustomizationField> getCustomizationFields() {
        return this.customizationFields;
    }
}
