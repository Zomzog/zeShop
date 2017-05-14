package bzh.zomzog.zeshop.cart.service.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import bzh.zomzog.zeshop.cart.domain.product.ProductCustomizationField;
import bzh.zomzog.zeshop.cart.service.dto.product.ProductCustomizationFieldDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ProductCustomizationFieldMapper {

    ProductCustomizationFieldDTO mapDTO(ProductCustomizationField product);

    List<ProductCustomizationFieldDTO> mapDTO(List<ProductCustomizationField> products);

    ProductCustomizationField map(ProductCustomizationFieldDTO productDTO);

    List<ProductCustomizationField> map(List<ProductCustomizationFieldDTO> productDTOs);

    ProductCustomizationField update(ProductCustomizationFieldDTO productDTO,
            @MappingTarget ProductCustomizationField product);
}
