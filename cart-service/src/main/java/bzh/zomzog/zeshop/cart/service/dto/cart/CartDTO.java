package bzh.zomzog.zeshop.cart.service.dto.cart;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Cart entity.
 */
public class CartDTO implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 2916580300642837855L;

    private Long id;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private Set<CartProductDTO> products = new HashSet<>();

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<CartProductDTO> getProducts() {
        return products;
    }

    public void setProducts(final Set<CartProductDTO> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(final String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CartDTO cartDTO = (CartDTO) o;

        if (!Objects.equals(id, cartDTO.id)) {
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
        return "CartDTO{" + "id=" + id + ", createdDate='" + createdDate + "'" + ", updatedDate='" + updatedDate + "'"
                + '}';
    }
}
