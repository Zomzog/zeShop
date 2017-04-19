package bzh.zomzog.zeshop.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import bzh.zomzog.zeshop.auth.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = "authorities")
    Account findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<Account> findOneWithAuthoritiesByLogin(String login);
}
