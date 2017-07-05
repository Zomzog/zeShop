package bzh.zomzog.zeshop.cart.service.mapper.cart;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import bzh.zomzog.zeshop.cart.domain.cart.CartProduct;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartProductDTO;
import bzh.zomzog.zeshop.cart.service.mapper.product.ProductCustomizationDataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

/**
 * Mapper for the entity CartProduct and its DTO CartProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCustomizationDataMapper.class})
public interface CartProductMapper {

    @Mappings({ //
            @Mapping(source = "cart.id", target = "cartId"), //
    })
    CartProductDTO cartProductToCartProductDTO(CartProduct cartProduct);

    Set<CartProductDTO> cartProductsToCartProductDTOs(Set<CartProduct> cartProducts);

    @Mappings({ //
            @Mapping(source = "cartId", target = "cart"), //
    })
    CartProduct cartProductDTOToCartProduct(CartProductDTO cartProductDTO);

    Set<CartProduct> cartProductDTOsToCartProducts(Set<CartProductDTO> cartProductDTOs);

    default Cart cartFromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }

}
