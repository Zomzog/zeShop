package bzh.zomzog.zeshop.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bzh.zomzog.zeshop.config.Constants;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date")
    private ZonedDateTime resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "jhi_user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") })
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private final Set<Authority> authorities = new HashSet<>();

    /**
     * @param activated
     *            the activated to set
     */
    public void setActivated(final boolean activated) {
        this.activated = activated;
    }

    /**
     * @param resetDate
     *            the resetDate to set
     */
    public void setResetDate(final ZonedDateTime resetDate) {
        this.resetDate = resetDate;
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
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the langKey
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * @param langKey
     *            the langKey to set
     */
    public void setLangKey(final String langKey) {
        this.langKey = langKey;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     *            the imageUrl to set
     */
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return the activationKey
     */
    public String getActivationKey() {
        return activationKey;
    }

    /**
     * @param activationKey
     *            the activationKey to set
     */
    public void setActivationKey(final String activationKey) {
        this.activationKey = activationKey;
    }

    /**
     * @return the resetKey
     */
    public String getResetKey() {
        return resetKey;
    }

    /**
     * @param resetKey
     *            the resetKey to set
     */
    public void setResetKey(final String resetKey) {
        this.resetKey = resetKey;
    }

    /**
     * @return the activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * @return the resetDate
     */
    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    /**
     * @return the authorities
     */
    public Set<Authority> getAuthorities() {
        return authorities;
    }

}
