package bzh.zomzog.zeshop.cart.service;

import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import bzh.zomzog.zeshop.cart.domain.cart.CartProduct;
import bzh.zomzog.zeshop.cart.domain.cart.ProductCustomizationData;
import bzh.zomzog.zeshop.cart.domain.product.Product;
import bzh.zomzog.zeshop.cart.domain.product.ProductCustomizationField;
import bzh.zomzog.zeshop.cart.repository.CartRepository;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartProductDTO;
import bzh.zomzog.zeshop.cart.service.dto.product.ProductCustomizationDataDTO;
import bzh.zomzog.zeshop.cart.service.mapper.cart.CartMapper;
import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.exception.NotFoundException;
import bzh.zomzog.zeshop.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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
    public CartDTO findOne(final Long id) throws NotFoundException {
        this.log.debug("Request to get Cart : {}", id);
        final Cart cart = getCartById(id);
        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(cart);
        return cartDTO;
    }

    /**
     * Delete the cart by id.
     *
     * @param id the id of the entity
     */
    public void delete(final Long id) throws NotFoundException {
        this.log.debug("Request to delete Cart : {}", id);
        getCartById(id);
        this.cartRepository.delete(id);
    }

    /**
     * Update a product.
     *
     * @param cartDTO the entity to save
     * @return the persisted entity
     */
    public CartDTO update(final CartDTO cartDTO) throws NotFoundException, BadParameterException {
        this.log.debug("Request to save Product : {}", cartDTO);

        Cart cart = getCartById(cartDTO.getId());

        // Check if all products exists
        for (final CartProductDTO cartProduct : cartDTO.getProducts()) {
            checkProductIsValid(cartProduct);
        }
        cart = this.cartMapper.update(cartDTO, cart);
        linkCartProductWithCart(cart);

        // Manual update because sub entity won't trigger update @PreUpdate
        cart.setUpdatedDate(ZonedDateTime.now());

        cart = this.cartRepository.save(cart);
        final CartDTO result = this.cartMapper.cartToCartDTO(cart);
        return result;
    }

    /**
     * Check if the product is present and if it's valid
     *
     * @param cartProduct
     * @throws BadParameterException
     */
    private void checkProductIsValid(final CartProductDTO cartProduct) throws BadParameterException {
        final Optional<Product> product = this.productService.get(cartProduct.getProductId());
        if (product.isPresent()) {
            for (final ProductCustomizationDataDTO custo : cartProduct.getCustomizations()) {
                this.checkCustomizationIsValid(custo, product.get());
            }
        } else {
            throw new BadParameterException("cart.product", "id", cartProduct.getProductId().toString(), "not found");
        }
    }

    /**
     * Check if a customized data is allowed on the product
     *
     * @param custo
     * @param product
     * @throws BadParameterException
     */
    private void checkCustomizationIsValid(final ProductCustomizationDataDTO custo, final Product product) throws BadParameterException {
        final Optional<ProductCustomizationField> pc = product.getCustomizationFields()
                .stream()
                .filter(c -> c.getId().equals(custo.getCustomizationFieldId()))
                .findFirst();
        if (!pc.isPresent()) {
            throw new BadParameterException("cart.product.customization", "id", custo.getCustomizationFieldId().toString(), "not found");
        }
    }


    /**
     * Add product to cart
     *
     * @param cartId
     * @param productId
     * @return
     * @throws BadParameterException
     */
    public CartDTO addToCart(final Long cartId, final Long productId) throws NotFoundException, BadParameterException {
        final Cart cart = getCartById(cartId);
        final Optional<Product> product = this.productService.get(productId);
        if (!product.isPresent()) {
            throw new BadParameterException("product", "id", productId.toString());
        }
        final CartProduct cartProduct = new CartProduct();
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(1L);
        cartProduct.setCart(cart);
        cart.getProducts().add(cartProduct);

        // Manual update because sub entity won't trigger update @PreUpdate
        cart.setUpdatedDate(ZonedDateTime.now());

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
        for (final CartProduct cartProduct : cart.getProducts()) {
            cartProduct.setCart(cart);
            for (final ProductCustomizationData custo : cartProduct.getCustomizations()) {
                custo.setCartProduct(cartProduct);
            }
        }
        return cart;
    }

    /**
     * Search the cart and check if the current user is owner of the cart
     *
     * @param cartId cart's id
     */
    private Cart getCartById(final Long cartId) throws NotFoundException {
        final Cart cart = this.cartRepository.findOne(cartId);
        if (null == cart) {
//            throw new BadParameterException("cart", "id", cartId.toString());
            throw new NotFoundException("Cart not found");
        }
        if (!cart.getUserName().equals(SecurityUtils.getCurrentUserLogin())) {
            throw new AccessDeniedException("Not your cart!");
        }
        return cart;
    }
}
