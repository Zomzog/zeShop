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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import bzh.zomzog.zeshop.cart.domain.product.ProductCustomization;
import bzh.zomzog.zeshop.domain.product.Product;

/**
 * A CartProduct.
 */
@Entity
@Table(name = "cart_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @ManyToOne(optional = false)
    @NotNull
    private Product product;

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
        addedDate = now;
        cart.setUpdatedDate(now);
    }

    @PreUpdate
    void updateDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        cart.setUpdatedDate(now);
    }

    public CartProduct id(final Long id) {
        this.id = id;
        return this;
    }

    public CartProduct product(final Product product) {
        this.product = product;
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
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * @param cart
     *            the cart to set
     */
    public void setCart(final Cart cart) {
        this.cart = cart;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(final Product product) {
        this.product = product;
    }

    /**
     * @return the productCustomization
     */
    public ProductCustomization getProductCustomization() {
        return productCustomization;
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
        return quantity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CartProduct [" + (id != null ? "id=" + id + ", " : "") + (cart != null ? "cart=" + cart + ", " : "")
                + (product != null ? "product=" + product + ", " : "")
                + (productCustomization != null ? "productCustomization=" + productCustomization + ", " : "")
                + (quantity != null ? "quantity=" + quantity + ", " : "")
                + (addedDate != null ? "addedDate=" + addedDate : "") + "]";
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
        result = prime * result + ((addedDate == null) ? 0 : addedDate.hashCode());
        result = prime * result + ((cart == null) ? 0 : cart.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + ((productCustomization == null) ? 0 : productCustomization.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
        if (other.id == null || id == null) {
            return false;
        }
        if (addedDate == null) {
            if (other.addedDate != null) {
                return false;
            }
        } else if (!addedDate.equals(other.addedDate)) {
            return false;
        }
        if (cart == null) {
            if (other.cart != null) {
                return false;
            }
        } else if (!cart.equals(other.cart)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (product == null) {
            if (other.product != null) {
                return false;
            }
        } else if (!product.equals(other.product)) {
            return false;
        }
        if (productCustomization == null) {
            if (other.productCustomization != null) {
                return false;
            }
        } else if (!productCustomization.equals(other.productCustomization)) {
            return false;
        }
        if (quantity == null) {
            if (other.quantity != null) {
                return false;
            }
        } else if (!quantity.equals(other.quantity)) {
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
        return addedDate;
    }

    /**
     * @param addedDate
     *            the addedDate to set
     */
    public void setAddedDate(final ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

}
