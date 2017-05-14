package bzh.zomzog.zeshop.cart.domain.cart;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import bzh.zomzog.zeshop.cart.domain.product.ProductCustomization;

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

    @OneToOne
    @JoinColumn(unique = true)
    private ProductCustomization productCustomization;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @NotNull
    @Column(name = "added_date", nullable = false)
    private ZonedDateTime addedDate;

    @PrePersist
    void addedDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        this.addedDate = now;
        this.cart.setUpdatedDate(now);
    }

    @PreUpdate
    void updateDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        this.cart.setUpdatedDate(now);
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

    public CartProduct productCustomization(final ProductCustomization productCustomization) {
        this.productCustomization = productCustomization;
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
     * @param id
     *            the id to set
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
     * @param cart
     *            the cart to set
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
     * @param productId
     *            the productId to set
     */
    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    /**
     * @return the productCustomization
     */
    public ProductCustomization getProductCustomization() {
        return this.productCustomization;
    }

    /**
     * @param productCustomization
     *            the productCustomization to set
     */
    public void setProductCustomization(final ProductCustomization productCustomization) {
        this.productCustomization = productCustomization;
    }

    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return this.quantity;
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
                + (this.productCustomization != null ? "productCustomization=" + this.productCustomization + ", " : "")
                + (this.quantity != null ? "quantity=" + this.quantity + ", " : "")
                + (this.addedDate != null ? "addedDate=" + this.addedDate : "") + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.addedDate == null) ? 0 : this.addedDate.hashCode());
        result = prime * result + ((this.cart == null || this.cart.getId() == null) ? 0 : this.cart.getId().hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.productId == null) ? 0 : this.productId.hashCode());
        result = prime * result + ((this.productCustomization == null) ? 0 : this.productCustomization.hashCode());
        result = prime * result + ((this.quantity == null) ? 0 : this.quantity.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartProduct other = (CartProduct) obj;
        if (other.id == null || this.id == null) {
            return false;
        }
        if (this.addedDate == null) {
            if (other.addedDate != null) {
                return false;
            }
        } else if (!this.addedDate.equals(other.addedDate)) {
            return false;
        }
        if (this.cart == null) {
            if (other.cart != null) {
                return false;
            }
        } else if (!this.cart.equals(other.cart)) {
            return false;
        }
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.productId == null) {
            if (other.productId != null) {
                return false;
            }
        } else if (!this.productId.equals(other.productId)) {
            return false;
        }
        if (this.productCustomization == null) {
            if (other.productCustomization != null) {
                return false;
            }
        } else if (!this.productCustomization.equals(other.productCustomization)) {
            return false;
        }
        if (this.quantity == null) {
            if (other.quantity != null) {
                return false;
            }
        } else if (!this.quantity.equals(other.quantity)) {
            return false;
        }
        return true;
    }

    /**
     * @param quantity
     *            the quantity to set
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
     * @param addedDate
     *            the addedDate to set
     */
    public void setAddedDate(final ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

}
