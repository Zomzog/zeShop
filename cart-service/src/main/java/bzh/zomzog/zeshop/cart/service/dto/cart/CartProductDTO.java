package bzh.zomzog.zeshop.cart.service.dto.cart;

import bzh.zomzog.zeshop.cart.service.dto.product.ProductCustomizationDataDTO;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the CartProduct entity.
 */
@ToString
public class CartProductDTO implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 4593742702575259124L;

    private Long id;

    private Long cartId;

    private Long productId;

    private final Set<ProductCustomizationDataDTO> customizations = new HashSet<>();

    @NotNull
    @Min(value = 1)
    private Long quantity;

    @NotNull
    private ZonedDateTime addedDate;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getAddedDate() {
        return this.addedDate;
    }

    public void setAddedDate(final ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public Long getCartId() {
        return this.cartId;
    }

    public void setCartId(final Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public Set<ProductCustomizationDataDTO> getCustomizations() {
        return this.customizations;
    }
}
