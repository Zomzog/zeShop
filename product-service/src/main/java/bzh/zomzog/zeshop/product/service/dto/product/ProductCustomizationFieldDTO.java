package bzh.zomzog.zeshop.product.service.dto.product;

import bzh.zomzog.zeshop.product.domain.ProductCustomizationField;
import bzh.zomzog.zeshop.product.domain.enums.ProductCustomizationType;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link ProductCustomizationField}
 *
 * @author Zomzog
 */
@ToString
public class ProductCustomizationFieldDTO implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 2955028673095050570L;

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
     * @param id the id to set
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
     * @param productId the productId to set
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
