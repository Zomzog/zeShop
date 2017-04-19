package bzh.zomzog.zeshop.domain.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product customization field.
 */
@Entity
@Table(name = "product_customization_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductCustomizationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private ProductCustomizationField field;

    @ManyToOne(optional = false)
    @NotNull
    private ProductCustomization customization;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    public ProductCustomizationData id(final Long id) {
        this.id = id;
        return this;
    }

    public ProductCustomizationData field(final ProductCustomizationField field) {
        this.field = field;
        return this;
    }

    public ProductCustomizationData customization(final ProductCustomization customization) {
        this.customization = customization;
        return this;
    }

    public ProductCustomizationData value(final String value) {
        this.value = value;
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
     * @return the field
     */
    public ProductCustomizationField getField() {
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(final ProductCustomizationField field) {
        this.field = field;
    }

    /**
     * @return the customization
     */
    public ProductCustomization getCustomization() {
        return customization;
    }

    /**
     * @param customization
     *            the customization to set
     */
    public void setCustomization(final ProductCustomization customization) {
        this.customization = customization;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

}
