package bzh.zomzog.zeshop.service.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.domain.product.Product;
import bzh.zomzog.zeshop.service.dto.product.ProductDTO;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = { ProductCustomizationFieldMapper.class })
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Mappings({ //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "updatedDate", ignore = true), //
    })
    Product productDTOToProduct(ProductDTO productDTO);

    List<Product> productDTOsToProducts(List<ProductDTO> productDTOs);

    @Mappings({ //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "updatedDate", ignore = true), //
    })
    Product update(ProductDTO productDTO, @MappingTarget Product product);
}
