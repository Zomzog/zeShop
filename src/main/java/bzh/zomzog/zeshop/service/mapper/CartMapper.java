package bzh.zomzog.zeshop.service.mapper;

import bzh.zomzog.zeshop.domain.*;
import bzh.zomzog.zeshop.service.dto.CartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Cart and its DTO CartDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, UserMapper.class, })
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CartDTO cartToCartDTO(Cart cart);

    List<CartDTO> cartsToCartDTOs(List<Cart> carts);

    @Mapping(source = "userId", target = "user")
    Cart cartDTOToCart(CartDTO cartDTO);

    List<Cart> cartDTOsToCarts(List<CartDTO> cartDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
