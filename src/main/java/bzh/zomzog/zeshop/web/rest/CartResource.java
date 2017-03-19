package bzh.zomzog.zeshop.web.rest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import bzh.zomzog.zeshop.service.CartService;
import bzh.zomzog.zeshop.service.dto.CartDTO;
import bzh.zomzog.zeshop.web.rest.util.HeaderUtil;
import bzh.zomzog.zeshop.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
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
    @Timed
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody final CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cartDTO);
        if (cartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cart cannot already have an ID"))
                    .body(null);
        }
        final CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
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
    @Timed
    public ResponseEntity<CartDTO> updateCart(@Valid @RequestBody final CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cartDTO);
        if (cartDTO.getId() == null) {
            return createCart(cartDTO);
        }
        final CartDTO result = cartService.save(cartDTO);
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
    @Timed
    public ResponseEntity<List<CartDTO>> getAllCarts(@ApiParam final Pageable pageable) {
        log.debug("REST request to get a page of Carts");
        final Page<CartDTO> page = cartService.findAll(pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carts");
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
    @Timed
    public ResponseEntity<CartDTO> getCart(@PathVariable final Long id) {
        log.debug("REST request to get Cart : {}", id);
        final CartDTO cartDTO = cartService.findOne(id);
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
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable final Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
