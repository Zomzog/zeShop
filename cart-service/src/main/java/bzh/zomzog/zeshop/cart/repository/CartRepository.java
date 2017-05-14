package bzh.zomzog.zeshop.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;

/**
 * Spring Data JPA repository for the Cart entity.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select distinct cart from Cart cart left join fetch cart.products")
    List<Cart> findAllWithEagerRelationships();

    @Query("select cart from Cart cart left join fetch cart.products where cart.id =:id")
    Cart findOneWithEagerRelationships(@Param("id") Long id);

}
