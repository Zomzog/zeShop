package bzh.zomzog.zeshop.cart.service.mapper.cart;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartDTO;

/**
 * Mapper for the entity Cart and its DTO CartDTO.
 */
@Mapper(componentModel = "spring", uses = { CartProductMapper.class })
public interface CartMapper {

    CartDTO cartToCartDTO(Cart cart);

    List<CartDTO> cartsToCartDTOs(List<Cart> carts);

    @Mappings({ //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "updatedDate", ignore = true), //
    })
    Cart cartDTOToCart(CartDTO cartDTO);

    List<Cart> cartDTOsToCarts(List<CartDTO> cartDTOs);

    @Mappings({ //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "updatedDate", ignore = true), //
    })
    Cart update(CartDTO cartDTO, @MappingTarget Cart cart);
}
