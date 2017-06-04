package bzh.zomzog.zeshop.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bzh.zomzog.zeshop.product.domain.Product;

/**
 * Spring Data JPA repository for the Cart entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct product from Product product left join fetch product.customizationFields")
    List<Product> findAllWithEagerRelationships();

    @Query("select product from Product product left join fetch product.customizationFields where product.id =:id")
    Product findOneWithEagerRelationships(@Param("id") Long id);

}
