package bzh.zomzog.zeshop.auth.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;

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

import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.auth.service.AccountService;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import bzh.zomzog.zeshop.configuration.AuthoritiesConstants;
import bzh.zomzog.zeshop.web.rest.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountResourceTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountResource accountResource = new AccountResource(this.accountService);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
    }

    // @Autowired
    private MockMvc restMvc;

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
    }

}
