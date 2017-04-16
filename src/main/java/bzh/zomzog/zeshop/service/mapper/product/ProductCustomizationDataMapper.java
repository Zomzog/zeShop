package bzh.zomzog.zeshop.service.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import bzh.zomzog.zeshop.domain.product.ProductCustomizationData;
import bzh.zomzog.zeshop.service.dto.product.ProductCustomizationDataDTO;

@Mapper(componentModel = "spring", uses = { ProductCustomizationFieldMapper.class })
public interface ProductCustomizationDataMapper {

    ProductCustomizationDataDTO mapDTO(ProductCustomizationData product);

    List<ProductCustomizationDataDTO> mapDTO(List<ProductCustomizationData> products);

    ProductCustomizationData map(ProductCustomizationDataDTO productDTO);

    List<ProductCustomizationData> map(List<ProductCustomizationDataDTO> productDTOs);

    ProductCustomizationData update(ProductCustomizationDataDTO productDTO,
            @MappingTarget ProductCustomizationData product);
}
