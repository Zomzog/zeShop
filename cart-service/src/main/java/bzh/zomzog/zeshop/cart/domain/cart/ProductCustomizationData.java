package bzh.zomzog.zeshop.cart.domain.cart;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Product customization field.
 */
@Entity
@Table(name = "product_customization_data")
public class ProductCustomizationData implements Serializable {

    /**
     * Serial Id
     */
    private static final long serialVersionUID = 5097545428325340620L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long customizationFieldId;

    @ManyToOne(optional = false)
    @NotNull
    private CartProduct cartProduct;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    public ProductCustomizationData id(final Long id) {
        this.id = id;
        return this;
    }

    public ProductCustomizationData customizationFieldId(final Long customizationFieldId) {
        this.customizationFieldId = customizationFieldId;
        return this;
    }

    public ProductCustomizationData cartProduct(final CartProduct cartProduct) {
        this.cartProduct = cartProduct;
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
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    public Long getCustomizationFieldId() {
        return this.customizationFieldId;
    }

    public void setCustomizationFieldId(final Long customizationFieldId) {
        this.customizationFieldId = customizationFieldId;
    }

    public CartProduct getCartProduct() {
        return this.cartProduct;
    }

    public void setCartProduct(final CartProduct cartProduct) {
        this.cartProduct = cartProduct;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }


}
