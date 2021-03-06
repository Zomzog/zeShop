package bzh.zomzog.zeshop.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "authority")
// TODO add cache
// @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Authority() {
    }

    public Authority(final String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Authority authority = (Authority) o;
        if (this.name == null) {
            return false;
        }
        if (!this.name.equals(authority.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return this.name != null ? this.name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Authority{" + "name='" + this.name + '\'' + "}";
    }
}
