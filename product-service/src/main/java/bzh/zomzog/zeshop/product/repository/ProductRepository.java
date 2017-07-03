package bzh.zomzog.zeshop.product.repository;

import bzh.zomzog.zeshop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cart entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct product from Product product left join fetch product.customizationFields")
    List<Product> findAllWithEagerRelationships();

    @Query("select product from Product product left join fetch product.customizationFields left join fetch product.images where product.id =:id")
    Product findOneWithEagerRelationships(@Param("id") Long id);

}
