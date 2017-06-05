package bzh.zomzog.zeshop.auth.service.dto;

import bzh.zomzog.zeshop.configuration.Constants;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * A DTO representing a account, with his authorities.
 */
public class AccountDTO {

    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String login;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey = "fr";

    private Set<String> authorities;

    public AccountDTO id(final Long id) {
        this.id = id;
        return this;
    }

    public AccountDTO login(final String login) {
        this.login = login;
        return this;
    }

    public AccountDTO activated(final boolean activated) {
        this.activated = activated;
        return this;
    }

    public AccountDTO langKey(final String langKey) {
        this.langKey = langKey;
        return this;
    }

    public AccountDTO authorities(final Set<String> authorities) {
        this.authorities = authorities;
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
     * @return the login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * @return the activated
     */
    public boolean isActivated() {
        return this.activated;
    }

    /**
     * @param activated the activated to set
     */
    public void setActivated(final boolean activated) {
        this.activated = activated;
    }

    /**
     * @return the langKey
     */
    public String getLangKey() {
        return this.langKey;
    }

    /**
     * @param langKey the langKey to set
     */
    public void setLangKey(final String langKey) {
        this.langKey = langKey;
    }

    /**
     * @return the authorities
     */
    public Set<String> getAuthorities() {
        return this.authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(final Set<String> authorities) {
        this.authorities = authorities;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountDTO [" + (this.id != null ? "id=" + this.id + ", " : "")
                + (this.login != null ? "login=" + this.login + ", " : "")
                + "activated=" + this.activated + ", " + (this.langKey != null ? "langKey=" + this.langKey + ", " : "")
                + (this.authorities != null ? "authorities=" + this.authorities : "") + "]";
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
        result = prime * result + (this.activated ? 1231 : 1237);
        result = prime * result + ((this.authorities == null) ? 0 : this.authorities.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.langKey == null) ? 0 : this.langKey.hashCode());
        result = prime * result + ((this.login == null) ? 0 : this.login.hashCode());
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
        final AccountDTO other = (AccountDTO) obj;
        if (this.activated != other.activated) {
            return false;
        }
        if (this.authorities == null) {
            if (other.authorities != null) {
                return false;
            }
        } else if (!this.authorities.equals(other.authorities)) {
            return false;
        }
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.langKey == null) {
            if (other.langKey != null) {
                return false;
            }
        } else if (!this.langKey.equals(other.langKey)) {
            return false;
        }
        if (this.login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!this.login.equals(other.login)) {
            return false;
        }
        return true;
    }

}
