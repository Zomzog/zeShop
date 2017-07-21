package bzh.zomzog.zeshop.cart.service.dto.cart;

import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the Cart entity.
 */
@ToString
public class CartDTO implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 2916580300642837855L;

    private Long id;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private Set<CartProductDTO> products = new HashSet<>();

    private String userName;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(final ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<CartProductDTO> getProducts() {
        return this.products;
    }

    public void setProducts(final Set<CartProductDTO> products) {
        this.products = products;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
}
