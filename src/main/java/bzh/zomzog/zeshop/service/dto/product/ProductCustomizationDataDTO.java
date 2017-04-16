package bzh.zomzog.zeshop.service.dto.product;

import bzh.zomzog.zeshop.domain.product.ProductCustomizationData;

/**
 * DTO for {@link ProductCustomizationData}
 * 
 * @author Zomzog
 *
 */
public class ProductCustomizationDataDTO {

    private Long id;

    private ProductCustomizationFieldDTO field;

    private Long customizationId;

    private String value;

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
     * @return the field
     */
    public ProductCustomizationFieldDTO getField() {
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(final ProductCustomizationFieldDTO field) {
        this.field = field;
    }

    /**
     * @return the customizationId
     */
    public Long getCustomizationId() {
        return customizationId;
    }

    /**
     * @param customizationId
     *            the customizationId to set
     */
    public void setCustomizationId(final Long customizationId) {
        this.customizationId = customizationId;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProductCustomizationDataDTO [" + (id != null ? "id=" + id + ", " : "")
                + (field != null ? "field=" + field + ", " : "")
                + (customizationId != null ? "customizationId=" + customizationId + ", " : "")
                + (value != null ? "value=" + value : "") + "]";
    }

}
