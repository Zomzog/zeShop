package bzh.zomzog.zeshop.cart.service.dto.product;

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
     * @return the field
     */
    public ProductCustomizationFieldDTO getField() {
        return this.field;
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
        return this.customizationId;
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
        return this.value;
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
        return "ProductCustomizationDataDTO [" + (this.id != null ? "id=" + this.id + ", " : "")
                + (this.field != null ? "field=" + this.field + ", " : "")
                + (this.customizationId != null ? "customizationId=" + this.customizationId + ", " : "")
                + (this.value != null ? "value=" + this.value : "") + "]";
    }

}
