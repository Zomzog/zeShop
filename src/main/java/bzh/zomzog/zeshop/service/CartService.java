package bzh.zomzog.zeshop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.domain.Cart;
import bzh.zomzog.zeshop.repository.CartRepository;
import bzh.zomzog.zeshop.service.dto.CartDTO;
import bzh.zomzog.zeshop.service.mapper.CartMapper;

/**
 * Service Implementation for managing Cart.
 */
@Service
@Transactional
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartService(final CartRepository cartRepository, final CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    /**
     * Save a cart.
     *
     * @param cartDTO
     *            the entity to save
     * @return the persisted entity
     */
    public CartDTO save(final CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        cart = cartRepository.save(cart);
        final CartDTO result = cartMapper.cartToCartDTO(cart);
        return result;
    }

    /**
     * Get all the carts.
     * 
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CartDTO> findAll(final Pageable pageable) {
        log.debug("Request to get all Carts");
        final Page<Cart> result = cartRepository.findAll(pageable);
        return result.map(cart -> cartMapper.cartToCartDTO(cart));
    }

    /**
     * Get one cart by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CartDTO findOne(final Long id) {
        log.debug("Request to get Cart : {}", id);
        final Cart cart = cartRepository.findOneWithEagerRelationships(id);
        final CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        return cartDTO;
    }

    /**
     * Delete the cart by id.
     *
     * @param id
     *            the id of the entity
     */
    public void delete(final Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.delete(id);
    }
}
