package bzh.zomzog.zeshop.auth.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Zomzog on 21/07/2017.
 */
@RunWith(SpringRunner.class)
public class DomainEqualsTest {

    @Test
    public void AccountEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Account.class);
    }

    @Test
    public void AuthorityEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authority.class);
    }
}
