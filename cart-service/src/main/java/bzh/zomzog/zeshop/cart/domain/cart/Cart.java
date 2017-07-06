package bzh.zomzog.zeshop.cart.domain.cart;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<CartProduct> products = new HashSet<>();

    private Long userId;

    @PrePersist
    void createdDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    void updatedDate() {
        this.updatedDate = ZonedDateTime.now();
    }

    public Cart id(final Long id) {
        this.id = id;
        return this;
    }

    public Cart createdDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Cart updatedDate(final ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

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
     * @return the createdDate
     */
    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the products
     */
    public Set<CartProduct> getProducts() {
        return this.products;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Cart [" + (this.id != null ? "id=" + this.id + ", " : "")
                + (this.createdDate != null ? "createdDate=" + this.createdDate + ", " : "")
                + (this.updatedDate != null ? "updatedDate=" + this.updatedDate + ", " : "")
                + "products=" + this.products + ", "
                + (this.userId != null ? "userId=" + this.userId : "")
                + "]";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Cart cart = (Cart) o;
        if (this.id == null && cart.id == null) {
            return false;
        }
        return this.id != null ? this.id.equals(cart.id) : cart.id == null;
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }
}
