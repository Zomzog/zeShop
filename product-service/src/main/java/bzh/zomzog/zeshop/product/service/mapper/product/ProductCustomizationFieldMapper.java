package bzh.zomzog.zeshop.product.service.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.product.domain.ProductCustomizationField;
import bzh.zomzog.zeshop.product.service.dto.product.ProductCustomizationFieldDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ProductCustomizationFieldMapper {

    @Mappings({
            @Mapping(target = "productId", source = "product.id")
    })
    ProductCustomizationFieldDTO mapDTO(ProductCustomizationField productCustomizationField);

    List<ProductCustomizationFieldDTO> mapDTO(List<ProductCustomizationField> products);

    @Mappings({
            @Mapping(target = "product.id", source = "productId")
    })
    ProductCustomizationField map(ProductCustomizationFieldDTO productDTO);

    List<ProductCustomizationField> map(List<ProductCustomizationFieldDTO> productDTOs);
}
