package bzh.zomzog.zeshop.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.zomzog.zeshop.auth.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
