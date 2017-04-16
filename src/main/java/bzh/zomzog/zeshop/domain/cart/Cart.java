package bzh.zomzog.zeshop.domain.cart;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import bzh.zomzog.zeshop.domain.User;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private final Set<CartProduct> products = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @PrePersist
    void createdDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    void updatedDate() {
        updatedDate = ZonedDateTime.now();
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
     * @return the createdDate
     */
    public ZonedDateTime getCreatedDate() {
        return createdDate;
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
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(final ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * @return the products
     */
    public Set<CartProduct> getProducts() {
        return products;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Cart [" + (id != null ? "id=" + id + ", " : "")
                + (createdDate != null ? "createdDate=" + createdDate + ", " : "")
                + (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "")
                + (products != null ? "products.size =" + products.size() + ", " : "")
                + (user != null ? "user=" + user : "") + "]";
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
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        if (other.id == null || id == null) {
            return false;
        }
        if (createdDate == null) {
            if (other.createdDate != null) {
                return false;
            }
        } else if (!createdDate.equals(other.createdDate)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (products == null) {
            if (other.products != null) {
                return false;
            }
        } else if (!products.equals(other.products)) {
            return false;
        }
        if (updatedDate == null) {
            if (other.updatedDate != null) {
                return false;
            }
        } else if (!updatedDate.equals(other.updatedDate)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

}
