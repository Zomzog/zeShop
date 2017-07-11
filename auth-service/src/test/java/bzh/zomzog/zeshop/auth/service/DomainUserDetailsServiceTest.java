package bzh.zomzog.zeshop.auth.service;

import bzh.zomzog.zeshop.auth.AuthServiceApplication;
import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.domain.Authority;
import bzh.zomzog.zeshop.auth.exception.UserNotActivatedException;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.configuration.AuthoritiesConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;

/**
 * Created by Zomzog on 11/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
public class DomainUserDetailsServiceTest {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "p@ssword";


    @Autowired
    private DomainUserDetailsService domainUserDetailsService;
    @MockBean
    private AccountRepository accountRepository;

    private Account account;

    @Before
    public void init() {
        this.account = new Account().id(1L).login(LOGIN).password(PASSWORD).activated(true);
        this.account.getAuthorities().add(new Authority(AuthoritiesConstants.ADMIN));
        this.account.getAuthorities().add(new Authority(AuthoritiesConstants.USER));
    }

    @Test
    public void loadUserByValidUsername() {
        // Init mocks
        BDDMockito.given(this.accountRepository.findOneWithAuthoritiesByLogin(anyString())).willReturn(Optional.of(this.account));

        final UserDetails result = this.domainUserDetailsService.loadUserByUsername(LOGIN);
        assertThat(result.getUsername()).isEqualTo(LOGIN);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
        final List<String> authorities = result.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        assertThat(authorities).containsExactly(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER);
    }

    @Test
    public void loadUserByValidUsernameNotActived() {
        // Init mocks
        BDDMockito.given(this.accountRepository.findOneWithAuthoritiesByLogin(anyString())).willReturn(Optional.of(this.account.activated(false)));

        try {
            this.domainUserDetailsService.loadUserByUsername(LOGIN);
            assertThat(false).isTrue();
        } catch (final Exception e) {
            assertThat(e).isInstanceOf(UserNotActivatedException.class);
        }
    }

    @Test
    public void loadUserByInvalid() {
        // Init mocks
        BDDMockito.given(this.accountRepository.findOneWithAuthoritiesByLogin(anyString())).willReturn(Optional.empty());

        try {
            this.domainUserDetailsService.loadUserByUsername(LOGIN);
            assertThat(false).isTrue();
        } catch (final Exception e) {
            assertThat(e).isInstanceOf(UsernameNotFoundException.class);
        }
    }
}
