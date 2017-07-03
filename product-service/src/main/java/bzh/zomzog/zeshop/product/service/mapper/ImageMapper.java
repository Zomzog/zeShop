package bzh.zomzog.zeshop.product.service.mapper;

import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.service.dto.ImageDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by Zomzog on 01/07/2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper {

    @Mappings({
            @Mapping(target = "productId", source = "product.id")
    })
    ImageDTO imageToImageDTO(Image image);

    @InheritInverseConfiguration
    Image imageDTOToImage(ImageDTO image);
}
