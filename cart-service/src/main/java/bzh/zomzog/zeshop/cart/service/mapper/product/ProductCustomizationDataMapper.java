package bzh.zomzog.zeshop.cart.service.mapper.product;

import bzh.zomzog.zeshop.cart.domain.cart.ProductCustomizationData;
import bzh.zomzog.zeshop.cart.service.dto.product.ProductCustomizationDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public abstract class ProductCustomizationDataMapper {

    @Mappings({
            @Mapping(target = "cartProductId", source = "product.cartProduct.id")
    })
    public abstract ProductCustomizationDataDTO mapDTO(ProductCustomizationData product);

    public abstract Set<ProductCustomizationDataDTO> mapDTO(Set<ProductCustomizationData> products);

    public abstract ProductCustomizationData map(ProductCustomizationDataDTO productDTO);

    public abstract Set<ProductCustomizationData> map(Set<ProductCustomizationDataDTO> productDTOs);

}
