package bzh.zomzog.zeshop.product.web.rest;

import bzh.zomzog.zeshop.product.service.ProductService;
import bzh.zomzog.zeshop.product.service.dto.product.ProductDTO;
import bzh.zomzog.zeshop.web.rest.utils.HeaderUtil;
import bzh.zomzog.zeshop.web.rest.utils.PaginationUtil;
import bzh.zomzog.zeshop.web.rest.utils.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Product.
 */
@RestController
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
     * @param productDTO the productDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new productDTO, or with status 400 (Bad Request) if the product
     * has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody final ProductDTO productDTO)
            throws URISyntaxException {
        this.log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new product cannot already have an ID"))
                    .body(null);
        }
        final ProductDTO result = this.productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /products : Updates an existing product.
     *
     * @param productDTO the productDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * productDTO, or with status 400 (Bad Request) if the productDTO is
     * not valid, or with status 500 (Internal Server Error) if the
     * productDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody final ProductDTO productDTO)
            throws URISyntaxException {
        this.log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            return createProduct(productDTO);
        }
        final ProductDTO result = this.productService.update(productDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDTO.getId().toString())).body(result);
    }

    /**
     * GET /products : get all the products.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of products
     * in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@ApiParam final Pageable pageable) {
        this.log.debug("REST request to get a page of Products");
        final Page<ProductDTO> page = this.productService.findAll(pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /products/:id : get the "id" product.
     *
     * @param id the id of the productDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * productDTO, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable final Long id) {
        this.log.debug("REST request to get Product : {}", id);
        final ProductDTO productDTO = this.productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productDTO));
    }

    /**
     * DELETE /products/:id : delete the "id" product.
     *
     * @param id the id of the productDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        this.log.debug("REST request to delete Product : {}", id);
        this.productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
