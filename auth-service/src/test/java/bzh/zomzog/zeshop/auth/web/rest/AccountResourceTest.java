package bzh.zomzog.zeshop.auth.web.rest;

import bzh.zomzog.zeshop.auth.AuthServiceApplication;
import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.domain.Authority;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.auth.service.AccountService;
import bzh.zomzog.zeshop.auth.service.MailService;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import bzh.zomzog.zeshop.auth.service.mapper.AccountMapper;
import bzh.zomzog.zeshop.auth.web.rest.error.ExceptionTranslator;
import bzh.zomzog.zeshop.common.configuration.AuthoritiesConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
public class AccountResourceTest {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Mock
    private MailService mockMailService;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private AccountService accountService;

    private MockMvc restMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(this.mockMailService).sendActivationEmail(anyObject());
        this.accountService = new AccountService(this.accountRepository, this.accountMapper, this.mockMailService);
        final AccountResource accountResource = new AccountResource(this.accountService);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource)
                .setCustomArgumentResolvers(this.pageableArgumentResolver)
                .setControllerAdvice(this.exceptionTranslator)
                .setMessageConverters(this.jacksonMessageConverter)
                .build();
    }

    @Test
    public void registerUser() throws Exception {
        final String login = "login";
        final String password = "password";
        final ManagedAccountDTO managedAccountDTO = new ManagedAccountDTO();
        managedAccountDTO.password(password)
                .login(login)
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList("ROLE_USER")));

        this.restMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedAccountDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.login").value("login"))
                // user inactive by default
                .andExpect(jsonPath("$.activated").value(false))
                .andExpect(jsonPath("$.langKey").value("fr"))
                // password must never be returned
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.USER));

        // One mail sent
        verify(this.mockMailService, times(1)).sendActivationEmail(anyObject());

        final Account created = this.accountRepository.findOneByLogin(login).get();
        assertThat(created.getPassword()).isNotNull();
        assertThat(created.getPassword()).isNotEqualTo(password);
        assertThat(created.getActivationKey()).isNotNull();

        // teardown
        final Optional<Account> newAccount = this.accountRepository.findOneByLogin("login");
        newAccount.ifPresent(this.accountRepository::delete);
    }

    @Test
    public void registerUserLoginAlreadyUsed() throws Exception {
        Account account = new Account().password("password")
                .login("alreadyused")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"))));
        account = this.accountRepository.save(account);

        final ManagedAccountDTO managedAccountDTO = new ManagedAccountDTO();
        managedAccountDTO.password("password")
                .login("alreadyused")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList("ROLE_USER")));

        this.restMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedAccountDTO)))
                .andExpect(status().isBadRequest());
        // No mail sent
        verify(this.mockMailService, times(0)).sendActivationEmail(anyObject());
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
    public void activateUser() throws Exception {
        final String activationKey = "activationKey$@!12";
        final String login = "activated";
        Account account = new Account().password("password")
                .login(login)
                .activated(false)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"))))
                .activationKey(activationKey);
        account = this.accountRepository.save(account);

        this.restMvc.perform(post("/activate")
                .param("key", activationKey))
                .andExpect(status().isOk());

        final Account created = this.accountRepository.findOne(account.getId());
        assertThat(created.isActivated()).isTrue();
        assertThat(created.getActivationKey()).isNull();
        // teardown
        this.accountRepository.delete(account);
    }

    @Test
    @Transactional
    public void listAccounts() throws Exception {
        Account account1 = new Account().password("password")
                .login("account1")
                .activated(true)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"))));
        account1 = this.accountRepository.save(account1);
        Account account2 = new Account().password("password")
                .login("account2")
                .activated(false)
                .langKey("fr")
                .authorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"))));
        account2 = this.accountRepository.save(account2);


        this.restMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItems(account1.getId().intValue(), account2.getId().intValue())));
    }


    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountDTO.class);
        TestUtil.equalsVerifier(ManagedAccountDTO.class);
        TestUtil.equalsVerifier(Account.class);
    }
}
