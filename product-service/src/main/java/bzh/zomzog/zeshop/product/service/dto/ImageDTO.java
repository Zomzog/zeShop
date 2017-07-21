package bzh.zomzog.zeshop.product.service.dto;

import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Zomzog on 01/07/2017.
 */
@ToString
public class ImageDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
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


}
