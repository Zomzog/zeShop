package bzh.zomzog.zeshop.cart.repository;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Cart entity.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select cart from Cart cart left join fetch cart.products product left join fetch product.customizations where cart.id =:id")
    Cart findOneWithEagerRelationships(@Param("id") Long id);


}
