package bzh.zomzog.zeshop.cart.domain.cart;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A CartProduct.
 */
@Entity
@Table(name = "cart_product")
public class CartProduct implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 7291013059525200543L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Cart cart;

    @NotNull
    private Long productId;

    @OneToMany(mappedBy = "cartProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<ProductCustomizationData> customizations = new HashSet<>();

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @NotNull
    @Column(name = "added_date", nullable = false)
    private ZonedDateTime addedDate;

    @NotNull
    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @PrePersist
    void createdDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        this.addedDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    void updatedDate() {
        this.updatedDate = ZonedDateTime.now();
    }

    public CartProduct id(final Long id) {
        this.id = id;
        return this;
    }

    public CartProduct productId(final Long productId) {
        this.productId = productId;
        return this;
    }

    public CartProduct cart(final Cart cart) {
        this.cart = cart;
        return this;
    }

    public CartProduct quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartProduct addedDate(final ZonedDateTime addedDate) {
        this.addedDate = addedDate;
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
     * @return the cart
     */
    public Cart getCart() {
        return this.cart;
    }

    /**
     * @param cart the cart to set
     */
    public void setCart(final Cart cart) {
        this.cart = cart;
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

    public Set<ProductCustomizationData> getCustomizations() {
        return this.customizations;
    }

    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return this.quantity;
    }


    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the addedDate
     */
    public ZonedDateTime getAddedDate() {
        return this.addedDate;
    }

    /**
     * @param addedDate the addedDate to set
     */
    public void setAddedDate(final ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CartProduct [" + (this.id != null ? "id=" + this.id + ", " : "")
                + (this.cart != null ? "cart=" + this.cart + ", " : "")
                + (this.productId != null ? "productId=" + this.productId + ", " : "")
                + (this.customizations != null ? "customizations=" + this.customizations + ", " : "")
                + (this.quantity != null ? "quantity=" + this.quantity + ", " : "")
                + (this.addedDate != null ? "addedDate=" + this.addedDate : "") + "]";
    }

}
