package bzh.zomzog.zeshop.cart.service;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import bzh.zomzog.zeshop.cart.domain.cart.CartProduct;
import bzh.zomzog.zeshop.cart.domain.product.Product;
import bzh.zomzog.zeshop.cart.repository.CartRepository;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.cart.service.mapper.cart.CartMapper;
import bzh.zomzog.zeshop.exception.BadParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Cart.
 */
@Service
@Transactional
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final ProductService productService;

    public CartService(final CartRepository cartRepository, final CartMapper cartMapper, final ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.productService = productService;
    }

    /**
     * Save a cart.
     *
     * @param cartDTO the entity to save
     * @return the persisted entity
     */
    public CartDTO save(final CartDTO cartDTO) {
        this.log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = this.cartMapper.cartDTOToCart(cartDTO);
        linkCartProductWithCart(cart);
        cart = this.cartRepository.save(cart);
        final CartDTO result = this.cartMapper.cartToCartDTO(cart);
        return result;
    }

    /**
     * Get all the carts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CartDTO> findAll(final Pageable pageable) {
        this.log.debug("Request to get all Carts");
        final Page<Cart> result = this.cartRepository.findAll(pageable);
        return result.map(cart -> this.cartMapper.cartToCartDTO(cart));
    }

    /**
     * Get one cart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CartDTO findOne(final Long id) {
        this.log.debug("Request to get Cart : {}", id);
        final Cart cart = this.cartRepository.findOneWithEagerRelationships(id);
        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(cart);
        return cartDTO;
    }

    /**
     * Delete the cart by id.
     *
     * @param id the id of the entity
     */
    public void delete(final Long id) {
        this.log.debug("Request to delete Cart : {}", id);
        this.cartRepository.delete(id);
    }

    /**
     * Update a product.
     *
     * @param cartDTO the entity to save
     * @return the persisted entity
     */
    public CartDTO update(final CartDTO cartDTO) {
        this.log.debug("Request to save Product : {}", cartDTO);
        Cart cart = this.cartRepository.findOne(cartDTO.getId());
        cart = this.cartMapper.update(cartDTO, cart);
        linkCartProductWithCart(cart);
        cart = this.cartRepository.save(cart);
        final CartDTO result = this.cartMapper.cartToCartDTO(cart);
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
        final Cart cart = this.cartRepository.findOne(cartId);
        if (null == cart) {
            throw new BadParameterException("cart", "id", cartId.toString());
        }
        final Optional<Product> product = this.productService.get(productId);
        if (!product.isPresent()) {
            throw new BadParameterException("product", "id", productId.toString());
        }
        final CartProduct cartProduct = new CartProduct();
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(1L);
        cartProduct.setCart(cart);
        cart.getProducts().add(cartProduct);
        this.cartRepository.save(cart);
        final CartDTO result = this.cartMapper.cartToCartDTO(cart);
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
