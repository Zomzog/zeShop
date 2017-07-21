package bzh.zomzog.zeshop.product.service.mapper.product;

import bzh.zomzog.zeshop.product.domain.Product;
import bzh.zomzog.zeshop.product.service.dto.product.ProductDTO;
import bzh.zomzog.zeshop.product.service.mapper.ImageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCustomizationFieldMapper.class, ImageMapper.class})
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);

    @Mappings({
    })
    Product productDTOToProduct(ProductDTO productDTO);

    @Mappings({
    })
    Product update(ProductDTO productDTO, @MappingTarget Product product);
}
