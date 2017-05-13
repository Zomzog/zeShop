package bzh.zomzog.zeshop.auth.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bzh.zomzog.zeshop.auth.AuthServiceApplication;
import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.domain.Authority;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.auth.service.AccountService;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import bzh.zomzog.zeshop.auth.web.rest.error.ExceptionTranslator;
import bzh.zomzog.zeshop.configuration.AuthoritiesConstants;
import bzh.zomzog.zeshop.web.rest.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
public class AccountResourceTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountResource accountResource = new AccountResource(this.accountService);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).setControllerAdvice(this.exceptionTranslator)
                .build();
    }

    @Test
    public void registerUser() throws Exception {
        final ManagedAccountDTO managedAccountDTO = new ManagedAccountDTO();
        managedAccountDTO.password("password")
                .login("login")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList("ROLE_USER")));

        this.restMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedAccountDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.activated").value(true))
                .andExpect(jsonPath("$.langKey").value("fr"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.USER));
        // teardown
        final Optional<Account> newAccount = this.accountRepository.findOneByLogin("login");
        newAccount.ifPresent(this.accountRepository::delete);
    }

    @Test
    public void registerUserLoginAlreadyUsed() throws Exception {
        Account account = new Account().password("password")
                .login("login")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"))));

        account = this.accountRepository.save(account);

        final ManagedAccountDTO managedAccountDTO = new ManagedAccountDTO();
        managedAccountDTO.password("password")
                .login("login")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList("ROLE_USER")));

        this.restMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedAccountDTO)))
                .andExpect(status().isBadRequest());

        // teardown
        this.accountRepository.delete(account);
    }

    @Test
    public void registerUserWithId() throws Exception {
        final ManagedAccountDTO managedAccountDTO = new ManagedAccountDTO();
        managedAccountDTO.password("password")
                .id(1L)
                .login("login")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList("ROLE_USER")));

        this.restMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedAccountDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void initResetPassword() throws Exception {
        Account account = new Account().password("password")
                .login("login")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"))));

        account = this.accountRepository.save(account);

        this.restMvc.perform(post("/account/reset_password/init")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
