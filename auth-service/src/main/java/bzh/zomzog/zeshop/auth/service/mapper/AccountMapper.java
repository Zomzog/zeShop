package bzh.zomzog.zeshop.auth.service.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.domain.Authority;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;

@Mapper(componentModel = "spring", uses = {})
public abstract class AccountMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "activationKey", ignore = true),
            @Mapping(target = "resetDate", ignore = true),
            @Mapping(target = "resetKey", ignore = true),
            @Mapping(target = "activated", ignore = true),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(managedAccountDTO.getPassword()))"),
    })
    public abstract Account map(ManagedAccountDTO managedAccountDTO);

    public abstract AccountDTO map(Account account);

    public abstract Set<String> map(Set<Authority> authorities);

    protected String map(final Authority authority) {
        return authority.getName();
    }

    protected Authority map(final String authority) {
        return new Authority(authority);
    }
}
