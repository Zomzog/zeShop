package bzh.zomzog.zeshop.product.service.dto.product;

import bzh.zomzog.zeshop.product.domain.enums.ProductCustomizationType;

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
        return this.id;
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
        return this.productId;
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
        return this.name;
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
        return this.type;
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
        return "ProductCustomizationDTO [" + (this.id != null ? "id=" + this.id + ", " : "")
                + (this.productId != null ? "productId=" + this.productId + ", " : "")
                + (this.name != null ? "name=" + this.name + ", " : "") + (this.type != null ? "type=" + this.type : "")
                + "]";
    }

}
