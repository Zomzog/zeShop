package bzh.zomzog.zeshop.auth.service.dto;

import bzh.zomzog.zeshop.common.configuration.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO wich extend {@link AccountDTO} for creation
 *
 * @author Zomzog
 */
public class ManagedAccountDTO extends AccountDTO {

    @NotNull
    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedAccountDTO password(final String password) {
        this.password = password;
        return this;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ManagedAccountDTO [" + (this.password != null ? "password=" + this.password + ", " : "")
                + (super.toString() != null ? "toString()=" + super.toString() : "") + "]";
    }

}
