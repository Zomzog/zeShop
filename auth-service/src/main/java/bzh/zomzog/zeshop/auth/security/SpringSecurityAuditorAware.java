package bzh.zomzog.zeshop.auth.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import bzh.zomzog.zeshop.configuration.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        final String userName = SecurityUtils.getCurrentUserLogin();
        return userName != null ? userName : Constants.SYSTEM_ACCOUNT;
    }
}