package bzh.zomzog.zeshop.cart.service.dto.product;

import bzh.zomzog.zeshop.cart.domain.cart.ProductCustomizationData;
import lombok.ToString;

/**
 * DTO for {@link ProductCustomizationData}
 *
 * @author Zomzog
 */
@ToString
public class ProductCustomizationDataDTO {
    private Long id;

    private Long customizationFieldId;

    private Long cartProductId;

    private String value;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getCustomizationFieldId() {
        return this.customizationFieldId;
    }

    public void setCustomizationFieldId(final Long customizationFieldId) {
        this.customizationFieldId = customizationFieldId;
    }

    public Long getCartProductId() {
        return this.cartProductId;
    }

    public void setCartProductId(final Long cartProductId) {
        this.cartProductId = cartProductId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
