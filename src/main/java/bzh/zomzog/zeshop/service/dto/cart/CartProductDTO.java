package bzh.zomzog.zeshop.service.dto.cart;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import bzh.zomzog.zeshop.service.dto.product.ProductCustomizationDTO;

/**
 * A DTO for the CartProduct entity.
 */
public class CartProductDTO implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 4593742702575259124L;

    private Long id;

    private Long cartId;

    private Long productId;

    private ProductCustomizationDTO productCustomization;

    @NotNull
    @Min(value = 1)
    private Long quantity;

    @NotNull
    private ZonedDateTime addedDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(final ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(final Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    /**
     * @return the productCustomization
     */
    public ProductCustomizationDTO getProductCustomization() {
        return productCustomization;
    }

    /**
     * @param productCustomization
     *            the productCustomization to set
     */
    public void setProductCustomization(final ProductCustomizationDTO productCustomization) {
        this.productCustomization = productCustomization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CartProductDTO cartProductDTO = (CartProductDTO) o;

        if (!Objects.equals(id, cartProductDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CartProductDTO{" + "id=" + id + ", quantity='" + quantity + "'" + ", addedDate='" + addedDate + "'"
                + '}';
    }
}
