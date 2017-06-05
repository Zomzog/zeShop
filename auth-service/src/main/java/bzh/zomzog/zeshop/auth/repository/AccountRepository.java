package bzh.zomzog.zeshop.auth.repository;

import bzh.zomzog.zeshop.auth.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<Account> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Account findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<Account> findOneWithAuthoritiesByLogin(String login);

    Optional<Account> findOneByActivationKey(String activationKey);
}
