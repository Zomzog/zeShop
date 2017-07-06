package bzh.zomzog.zeshop.cart.domain.product;


import bzh.zomzog.zeshop.cart.domain.enumeration.ProductCustomizationType;
import lombok.ToString;

/**
 * A Product customization field.
 */
@ToString
public class ProductCustomizationField {

    private Long id;

    private String name;

    private ProductCustomizationType type;

    public ProductCustomizationField id(final Long id) {
        this.id = id;
        return this;
    }

    public ProductCustomizationField name(final String name) {
        this.name = name;
        return this;
    }

    public ProductCustomizationField type(final ProductCustomizationType type) {
        this.type = type;
        return this;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public ProductCustomizationType getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final ProductCustomizationType type) {
        this.type = type;
    }

}
