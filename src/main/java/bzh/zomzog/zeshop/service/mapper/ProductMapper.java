package bzh.zomzog.zeshop.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.domain.Product;
import bzh.zomzog.zeshop.service.dto.ProductDTO;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Mappings({ @Mapping(target = "createdDate", ignore = true), })
    Product productDTOToProduct(ProductDTO productDTO);

    List<Product> productDTOsToProducts(List<ProductDTO> productDTOs);

    @Mappings({ @Mapping(target = "createdDate", ignore = true), })
    Product update(ProductDTO productDTO, @MappingTarget Product product);
}
