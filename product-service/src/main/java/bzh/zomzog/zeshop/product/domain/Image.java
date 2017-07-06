package bzh.zomzog.zeshop.product.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Zomzog on 30/06/2017.
 */
@Entity
@Table(name = "image")
public class Image implements Serializable {
    /**
     * Serial Id
     */
    private static final long serialVersionUID = -956293776651305297L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private Product product;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Image id(final Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Image name(final String name) {
        this.name = name;
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Image product(final Product product) {
        this.product = product;
        return this;
    }
}
