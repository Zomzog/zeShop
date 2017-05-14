package bzh.zomzog.zeshop.cart.service.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import bzh.zomzog.zeshop.cart.domain.product.ProductCustomization;
import bzh.zomzog.zeshop.cart.service.dto.product.ProductCustomizationDTO;

@Mapper(componentModel = "spring", uses = { ProductCustomizationDataMapper.class })
public interface ProductCustomizationMapper {

    ProductCustomizationDTO mapDTO(ProductCustomization product);

    List<ProductCustomizationDTO> mapDTO(List<ProductCustomization> products);

    ProductCustomization map(ProductCustomizationDTO productDTO);

    List<ProductCustomization> map(List<ProductCustomizationDTO> productDTOs);

    ProductCustomization update(ProductCustomizationDTO productDTO, @MappingTarget ProductCustomization product);
}
