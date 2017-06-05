package bzh.zomzog.zeshop.auth.service.mapper;

import bzh.zomzog.zeshop.auth.AuthServiceApplication;
import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Zomzog on 05/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void passwordEncrypted() {
        final String password = "test";
        final ManagedAccountDTO managedAccountDTO = new ManagedAccountDTO();
        managedAccountDTO.password(password);
        final Account account = this.accountMapper.map(managedAccountDTO);
        assertThat(account.getPassword()).isNotEqualTo(password);
        assertThat(account.getPassword()).isNotNull();
    }
}
