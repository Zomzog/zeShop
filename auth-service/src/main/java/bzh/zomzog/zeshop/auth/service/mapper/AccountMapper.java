package bzh.zomzog.zeshop.auth.service.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.domain.Authority;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;

@Mapper(componentModel = "spring", uses = {})
public interface AccountMapper {

    Account map(AccountDTO accountDTO);

    Account map(ManagedAccountDTO managedAccountDTO);

    AccountDTO map(Account account);

    Set<String> map(Set<Authority> authorities);

    default String map(final Authority authority) {
        return authority.getName();
    }

    default Authority map(final String authority) {
        return new Authority(authority);
    }
}
