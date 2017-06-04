package bzh.zomzog.zeshop.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import bzh.zomzog.zeshop.product.domain.enums.ProductCustomizationType;

/**
 * A Product customization field.
 */
@Entity
@Table(name = "product_customization_field")
public class ProductCustomizationField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Product product;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ProductCustomizationType type;

    public ProductCustomizationField id(final Long id) {
        this.id = id;
        return this;
    }

    public ProductCustomizationField product(final Product product) {
        this.product = product;
        return this;
    }

    public ProductCustomizationField name(final String name) {
        this.name = name;
        return this;
    }

    public ProductCustomizationField type(final ProductCustomizationType type) {
        this.type = type;
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
     * @return the product
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(final Product product) {
        this.product = product;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public ProductCustomizationType getType() {
        return this.type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final ProductCustomizationType type) {
        this.type = type;
    }

}
