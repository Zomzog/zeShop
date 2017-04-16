package bzh.zomzog.zeshop.service.dto.product;

import bzh.zomzog.zeshop.domain.enumeration.ProductCustomizationType;
import bzh.zomzog.zeshop.domain.product.ProductCustomization;

/**
 * DTO for {@link ProductCustomization}
 * 
 * @author Zomzog
 *
 */
public class ProductCustomizationFieldDTO {

    private Long id;

    private Long productId;

    private String name;

    private ProductCustomizationType type;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public ProductCustomizationType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final ProductCustomizationType type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProductCustomizationDTO [" + (id != null ? "id=" + id + ", " : "")
                + (productId != null ? "productId=" + productId + ", " : "")
                + (name != null ? "name=" + name + ", " : "") + (type != null ? "type=" + type : "") + "]";
    }

}
