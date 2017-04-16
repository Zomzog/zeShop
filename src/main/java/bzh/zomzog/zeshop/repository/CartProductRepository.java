package bzh.zomzog.zeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.zomzog.zeshop.domain.cart.CartProduct;

/**
 * Spring Data JPA repository for the CartProduct entity.
 */
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

}
