package bzh.zomzog.zeshop.cart.service.mapper.cart;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import bzh.zomzog.zeshop.cart.domain.cart.CartProduct;
import bzh.zomzog.zeshop.cart.domain.product.ProductCustomization;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartProductDTO;
import bzh.zomzog.zeshop.cart.service.mapper.product.ProductCustomizationFieldMapper;
import bzh.zomzog.zeshop.cart.service.mapper.product.ProductCustomizationMapper;

/**
 * Mapper for the entity CartProduct and its DTO CartProductDTO.
 */
@Mapper(componentModel = "spring", uses = { ProductCustomizationFieldMapper.class, ProductCustomizationMapper.class })
public interface CartProductMapper {

    @Mappings({ //
            @Mapping(source = "cart.id", target = "cartId"), //
    })
    CartProductDTO cartProductToCartProductDTO(CartProduct cartProduct);

    List<CartProductDTO> cartProductsToCartProductDTOs(List<CartProduct> cartProducts);

    @Mappings({ //
            @Mapping(source = "cartId", target = "cart"), //
    })
    CartProduct cartProductDTOToCartProduct(CartProductDTO cartProductDTO);

    List<CartProduct> cartProductDTOsToCartProducts(List<CartProductDTO> cartProductDTOs);

    default Cart cartFromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }

    default ProductCustomization productCustomizationFromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductCustomization productCustomization = new ProductCustomization();
        productCustomization.setId(id);
        return productCustomization;
    }
}
