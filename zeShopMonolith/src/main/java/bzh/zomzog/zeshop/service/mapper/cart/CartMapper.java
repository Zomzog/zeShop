package bzh.zomzog.zeshop.service.mapper.cart;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.domain.cart.Cart;
import bzh.zomzog.zeshop.domain.product.Product;
import bzh.zomzog.zeshop.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.service.mapper.UserMapper;
import bzh.zomzog.zeshop.service.mapper.product.ProductMapper;

/**
 * Mapper for the entity Cart and its DTO CartDTO.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, UserMapper.class, CartProductMapper.class })
public interface CartMapper {

    @Mappings({ //
            @Mapping(source = "user.id", target = "userId"), //
            @Mapping(source = "user.login", target = "userLogin"), //
    })
    CartDTO cartToCartDTO(Cart cart);

    List<CartDTO> cartsToCartDTOs(List<Cart> carts);

    @Mappings({ //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "updatedDate", ignore = true), //
            @Mapping(source = "userId", target = "user"), //
    })
    Cart cartDTOToCart(CartDTO cartDTO);

    List<Cart> cartDTOsToCarts(List<CartDTO> cartDTOs);

    default Product productFromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Product product = new Product();
        product.setId(id);
        return product;
    }

    @Mappings({ //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "updatedDate", ignore = true), //
            @Mapping(source = "userId", target = "user"),//
    })
    Cart update(CartDTO cartDTO, @MappingTarget Cart cart);
}
