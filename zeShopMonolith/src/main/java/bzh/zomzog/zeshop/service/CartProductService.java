package bzh.zomzog.zeshop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.domain.cart.CartProduct;
import bzh.zomzog.zeshop.repository.CartProductRepository;
import bzh.zomzog.zeshop.service.dto.cart.CartProductDTO;
import bzh.zomzog.zeshop.service.mapper.cart.CartProductMapper;

/**
 * Service Implementation for managing CartProduct.
 */
@Service
@Transactional
public class CartProductService {

    private final Logger log = LoggerFactory.getLogger(CartProductService.class);

    private final CartProductRepository cartProductRepository;

    private final CartProductMapper cartProductMapper;

    public CartProductService(final CartProductRepository cartProductRepository,
            final CartProductMapper cartProductMapper) {
        this.cartProductRepository = cartProductRepository;
        this.cartProductMapper = cartProductMapper;
    }

    /**
     * Save a cartProduct.
     *
     * @param cartProductDTO
     *            the entity to save
     * @return the persisted entity
     */
    public CartProductDTO save(final CartProductDTO cartProductDTO) {
        log.debug("Request to save CartProduct : {}", cartProductDTO);
        CartProduct cartProduct = cartProductMapper.cartProductDTOToCartProduct(cartProductDTO);
        cartProduct = cartProductRepository.save(cartProduct);
        final CartProductDTO result = cartProductMapper.cartProductToCartProductDTO(cartProduct);
        return result;
    }

    /**
     * Get all the cartProducts.
     * 
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CartProductDTO> findAll(final Pageable pageable) {
        log.debug("Request to get all CartProducts");
        final Page<CartProduct> result = cartProductRepository.findAll(pageable);
        return result.map(cartProduct -> cartProductMapper.cartProductToCartProductDTO(cartProduct));
    }

    /**
     * Get one cartProduct by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CartProductDTO findOne(final Long id) {
        log.debug("Request to get CartProduct : {}", id);
        final CartProduct cartProduct = cartProductRepository.findOne(id);
        final CartProductDTO cartProductDTO = cartProductMapper.cartProductToCartProductDTO(cartProduct);
        return cartProductDTO;
    }

    /**
     * Delete the cartProduct by id.
     *
     * @param id
     *            the id of the entity
     */
    public void delete(final Long id) {
        log.debug("Request to delete CartProduct : {}", id);
        cartProductRepository.delete(id);
    }
}
