package bzh.zomzog.zeshop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.domain.cart.Cart;
import bzh.zomzog.zeshop.domain.cart.CartProduct;
import bzh.zomzog.zeshop.domain.product.Product;
import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.repository.CartRepository;
import bzh.zomzog.zeshop.repository.ProductRepository;
import bzh.zomzog.zeshop.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.service.mapper.cart.CartMapper;

/**
 * Service Implementation for managing Cart.
 */
@Service
@Transactional
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository    cartRepository;
    private final ProductRepository productRepository;

    private final CartMapper cartMapper;

    public CartService(final CartRepository cartRepository, final CartMapper cartMapper,
            final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
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
        linkCartProductWithCart(cart);
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

    /**
     * Update a product.
     *
     * @param cartDTO
     *            the entity to save
     * @return the persisted entity
     */
    public CartDTO update(final CartDTO cartDTO) {
        log.debug("Request to save Product : {}", cartDTO);
        Cart cart = cartRepository.findOne(cartDTO.getId());
        cart = cartMapper.update(cartDTO, cart);
        linkCartProductWithCart(cart);
        cart = cartRepository.save(cart);
        final CartDTO result = cartMapper.cartToCartDTO(cart);
        return result;
    }

    /**
     * Add product to cart
     * 
     * @param cartId
     * @param productId
     * @return
     * @throws BadParameterException
     */
    public CartDTO addToCart(final Long cartId, final Long productId) throws BadParameterException {
        final Cart cart = cartRepository.findOne(cartId);
        final Product product = productRepository.findOne(productId);
        if (null == cart) {
            throw new BadParameterException("cart", "id", cartId.toString());
        }
        if (null == product) {
            throw new BadParameterException("product", "id", productId.toString());
        }
        final CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(1L);
        cartProduct.setCart(cart);
        cart.getProducts().add(cartProduct);
        cartRepository.save(cart);
        final CartDTO result = cartMapper.cartToCartDTO(cart);
        return result;
    }

    /**
     * Add for all product cart the reference of the cart
     * 
     * @param cart
     * @return
     */
    private Cart linkCartProductWithCart(final Cart cart) {
        for (final CartProduct product : cart.getProducts()) {
            product.setCart(cart);
        }
        return cart;
    }
}
