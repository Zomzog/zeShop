package bzh.zomzog.zeshop.cart.domain.cart;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
     * @param id
     *            the id to set
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
     * @param createdDate
     *            the createdDate to set
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
     * @param updatedDate
     *            the updatedDate to set
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
     * @param userId
     *            the userId to set
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
                + (this.products != null ? "products=" + this.products + ", " : "")
                + (this.userId != null ? "userId=" + this.userId : "")
                + "]";
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
        result = prime * result + ((this.createdDate == null) ? 0 : this.createdDate.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.products == null) ? 0 : this.products.hashCode());
        result = prime * result + ((this.updatedDate == null) ? 0 : this.updatedDate.hashCode());
        result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
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
        final Cart other = (Cart) obj;
        if (this.createdDate == null) {
            if (other.createdDate != null) {
                return false;
            }
        } else if (!this.createdDate.equals(other.createdDate)) {
            return false;
        }
        if (this.id == null) {
            return false;
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.products == null) {
            if (other.products != null) {
                return false;
            }
        } else if (!this.products.equals(other.products)) {
            return false;
        }
        if (this.updatedDate == null) {
            if (other.updatedDate != null) {
                return false;
            }
        } else if (!this.updatedDate.equals(other.updatedDate)) {
            return false;
        }
        if (this.userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!this.userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

}
