package bzh.zomzog.zeshop.product.service.mapper;

import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.service.dto.ImageDTO;
import org.mapstruct.Mapper;

/**
 * Created by Zomzog on 01/07/2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper {

    ImageDTO map(Image image);

    Image map(ImageDTO image);
}
