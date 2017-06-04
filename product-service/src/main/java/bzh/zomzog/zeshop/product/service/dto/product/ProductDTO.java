package bzh.zomzog.zeshop.product.service.dto.product;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1738406971845316111L;

    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    private String description;

    private int quantity;

    private float price;

    private boolean available;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private Set<ProductCustomizationFieldDTO> customizationFields = new HashSet<>();

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(final float price) {
        this.price = price;
    }

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available
     *            the available to set
     */
    public void setAvailable(final boolean available) {
        this.available = available;
    }

    /**
     * @return the createdDate
     */
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(final ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the customizationFields
     */
    public Set<ProductCustomizationFieldDTO> getCustomizationFields() {
        return customizationFields;
    }

    /**
     * @param customizationFields
     *            the customizationFields to set
     */
    public void setCustomizationFields(final Set<ProductCustomizationFieldDTO> customizationFields) {
        this.customizationFields = customizationFields;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProductDTO [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
                + (description != null ? "description=" + description + ", " : "") + "quantity=" + quantity + ", price="
                + price + ", available=" + available + ", "
                + (createdDate != null ? "createdDate=" + createdDate + ", " : "")
                + (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "")
                + (customizationFields != null ? "customizationFields=" + customizationFields : "") + "]";
    }
}
