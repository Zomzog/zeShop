package bzh.zomzog.zeshop.product.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {
    /**
     * Serial Id
     */
    private static final long serialVersionUID = -8932422047406749176L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private float price;

    @Column(name = "available")
    private boolean available;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private ZonedDateTime updatedDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<ProductCustomizationField> customizationFields = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_image",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "id")})
    private final Set<Image> images = new HashSet<>();

    @PrePersist
    void createdDate() {
        this.createdDate = ZonedDateTime.now();
    }

    @PreUpdate
    void updatedDate() {
        this.updatedDate = ZonedDateTime.now();
    }

    public Product name(final String name) {
        this.name = name;
        return this;
    }

    public Product description(final String description) {
        this.description = description;
        return this;
    }

    public Product quantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product price(final float price) {
        this.price = price;
        return this;
    }

    public Product available(final boolean available) {
        this.available = available;
        return this;
    }

    public Product createdDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Product updatedDate(final ZonedDateTime updatedDate) {
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
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return this.price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(final float price) {
        this.price = price;
    }

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(final boolean available) {
        this.available = available;
    }

    /**
     * @return the createdDate
     */
    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    /**
     * @return the updatedDate
     */
    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * @return the customizationFields
     */
    public Set<ProductCustomizationField> getCustomizationFields() {
        return this.customizationFields;
    }

    public Set<Image> getImages() {
        return this.images;
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
        result = prime * result + (this.available ? 1231 : 1237);
        result = prime * result + ((this.createdDate == null) ? 0 : this.createdDate.hashCode());
        result = prime * result + this.customizationFields.hashCode();
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + Float.floatToIntBits(this.price);
        result = prime * result + this.quantity;
        result = prime * result + ((this.updatedDate == null) ? 0 : this.updatedDate.hashCode());
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
        final Product other = (Product) obj;
        if (other.id == null || this.id == null) {
            return false;
        }
        if (this.available != other.available) {
            return false;
        }
        if (this.createdDate == null) {
            if (other.createdDate != null) {
                return false;
            }
        } else if (!this.createdDate.equals(other.createdDate)) {
            return false;
        }
        if (!this.customizationFields.equals(other.customizationFields)) {
            return false;
        }
        if (this.description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!this.description.equals(other.description)) {
            return false;
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (Float.floatToIntBits(this.price) != Float.floatToIntBits(other.price)) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (this.updatedDate == null) {
            if (other.updatedDate != null) {
                return false;
            }
        } else if (!this.updatedDate.equals(other.updatedDate)) {
            return false;
        }
        return true;
    }

}
