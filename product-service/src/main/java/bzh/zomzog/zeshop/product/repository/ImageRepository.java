package bzh.zomzog.zeshop.product.repository;

import bzh.zomzog.zeshop.product.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zomzog on 01/07/2017.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
