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

import bzh.zomzog.zeshop.service.ProductService;
import bzh.zomzog.zeshop.service.dto.ProductDTO;
import bzh.zomzog.zeshop.web.rest.util.HeaderUtil;
import bzh.zomzog.zeshop.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    private final ProductService productService;

    public ProductResource(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * POST /products : Create a new product.
     *
     * @param productDTO
     *            the productDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     *         new productDTO, or with status 400 (Bad Request) if the product
     *         has already an ID
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    @Timed
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody final ProductDTO productDTO)
            throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new product cannot already have an ID"))
                    .body(null);
        }
        final ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /products : Updates an existing product.
     *
     * @param productDTO
     *            the productDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         productDTO, or with status 400 (Bad Request) if the productDTO is
     *         not valid, or with status 500 (Internal Server Error) if the
     *         productDTO couldnt be updated
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    @Timed
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody final ProductDTO productDTO)
            throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            return createProduct(productDTO);
        }
        final ProductDTO result = productService.update(productDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDTO.getId().toString())).body(result);
    }

    /**
     * GET /products : get all the products.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of products
     *         in body
     * @throws URISyntaxException
     *             if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/products")
    @Timed
    public ResponseEntity<List<ProductDTO>> getAllProducts(@ApiParam final Pageable pageable) {
        log.debug("REST request to get a page of Products");
        final Page<ProductDTO> page = productService.findAll(pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /products/:id : get the "id" product.
     *
     * @param id
     *            the id of the productDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         productDTO, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    @Timed
    public ResponseEntity<ProductDTO> getProduct(@PathVariable final Long id) {
        log.debug("REST request to get Product : {}", id);
        final ProductDTO productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productDTO));
    }

    /**
     * DELETE /products/:id : delete the "id" product.
     *
     * @param id
     *            the id of the productDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
