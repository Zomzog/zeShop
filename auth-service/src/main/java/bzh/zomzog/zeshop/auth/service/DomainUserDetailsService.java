package bzh.zomzog.zeshop.auth.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.exception.UserNotActivatedException;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final AccountRepository accountRepository;

    public DomainUserDetailsService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        final String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        final Optional<Account> accountFromDatabase = accountRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        return accountFromDatabase.map(account -> {
            if (!account.isActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            final List<GrantedAuthority> grantedAuthorities = account.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
            return new User(lowercaseLogin, account.getPassword(), grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException(
                "Account " + lowercaseLogin + " was not found in the " + "database"));
    }
}