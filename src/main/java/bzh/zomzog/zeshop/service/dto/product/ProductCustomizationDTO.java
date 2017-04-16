package bzh.zomzog.zeshop.service.dto.product;

import java.util.HashSet;
import java.util.Set;

import bzh.zomzog.zeshop.domain.product.ProductCustomization;

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
     * @return the customizations
     */
    public Set<ProductCustomizationDataDTO> getCustomizations() {
        return customizations;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProductCustomizationDTO [" + (id != null ? "id=" + id + ", " : "")
                + (customizations != null ? "customizations=" + customizations : "") + "]";
    }

}
