package bzh.zomzog.zeshop.cart.service.dto.product;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link ProductCustomization}
 * 
 * @author Zomzog
 *
 */
public class ProductCustomizationDTO {

    private Long id;

    private final Set<ProductCustomizationDataDTO> customizations = new HashSet<>();

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
     * @return the customizations
     */
    public Set<ProductCustomizationDataDTO> getCustomizations() {
        return this.customizations;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProductCustomizationDTO [" + (this.id != null ? "id=" + this.id + ", " : "")
                + (this.customizations != null ? "customizations=" + this.customizations : "") + "]";
    }

}
