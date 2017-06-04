package bzh.zomzog.zeshop.cart.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bzh.zomzog.zeshop.cart.service.CartService;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.web.rest.utils.HeaderUtil;
import bzh.zomzog.zeshop.web.rest.utils.PaginationUtil;
import bzh.zomzog.zeshop.web.rest.utils.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "cart";

    private final CartService cartService;

    public CartResource(final CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * POST /carts : Create a new cart.
     *
     * @param cartDTO
     *            the cartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     *         new cartDTO, or with status 400 (Bad Request) if the cart has
     *         already an ID
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PostMapping("/carts")
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody final CartDTO cartDTO) throws URISyntaxException {
        this.log.debug("REST request to save Cart : {}", cartDTO);
        if (cartDTO.getId() != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        final CartDTO result = this.cartService.save(cartDTO);
        return ResponseEntity.created(new URI("/carts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /carts : Updates an existing cart.
     *
     * @param cartDTO
     *            the cartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         cartDTO, or with status 400 (Bad Request) if the cartDTO is not
     *         valid, or with status 500 (Internal Server Error) if the cartDTO
     *         couldnt be updated
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PutMapping("/carts")
    public ResponseEntity<CartDTO> updateCart(@Valid @RequestBody final CartDTO cartDTO) throws URISyntaxException {
        this.log.debug("REST request to update Cart : {}", cartDTO);
        if (cartDTO.getId() == null) {
            return createCart(cartDTO);
        }
        final CartDTO result = this.cartService.update(cartDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /carts : get all the carts.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carts in
     *         body
     * @throws URISyntaxException
     *             if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts(@ApiParam final Pageable pageable) {
        this.log.debug("REST request to get a page of Carts");
        final Page<CartDTO> page = this.cartService.findAll(pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/carts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /carts/:id : get the "id" cart.
     *
     * @param id
     *            the id of the cartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         cartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carts/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable final Long id) {
        this.log.debug("REST request to get Cart : {}", id);
        final CartDTO cartDTO = this.cartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cartDTO));
    }

    /**
     * DELETE /carts/:id : delete the "id" cart.
     *
     * @param id
     *            the id of the cartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable final Long id) {
        this.log.debug("REST request to delete Cart : {}", id);
        this.cartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable final Long cartId, @PathVariable final Long productId)
            throws URISyntaxException, BadParameterException {
        this.log.debug("REST request to add to  Cart : {} product {}", cartId, productId);
        final CartDTO result = this.cartService.addToCart(cartId, productId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartId.toString()))
                .body(result);
    }
}
