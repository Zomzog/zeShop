package bzh.zomzog.zeshop.product.domain;

import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Zomzog on 30/06/2017.
 */
@ToString
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

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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
}
