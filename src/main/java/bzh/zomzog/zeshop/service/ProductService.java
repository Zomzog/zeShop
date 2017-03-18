package bzh.zomzog.zeshop.service;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.domain.Product;
import bzh.zomzog.zeshop.repository.ProductRepository;
import bzh.zomzog.zeshop.service.dto.ProductDTO;
import bzh.zomzog.zeshop.service.mapper.ProductMapper;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(final ProductRepository productRepository, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO
     *            the entity to save
     * @return the persisted entity
     */
    public ProductDTO save(final ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.productDTOToProduct(productDTO);
        // Fixe created date to now
        product.setCreatedDate(ZonedDateTime.now());
        product = productRepository.save(product);
        final ProductDTO result = productMapper.productToProductDTO(product);
        return result;
    }

    /**
     * Get all the products.
     * 
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(final Pageable pageable) {
        log.debug("Request to get all Products");
        final Page<Product> result = productRepository.findAll(pageable);
        return result.map(product -> productMapper.productToProductDTO(product));
    }

    /**
     * Get one product by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProductDTO findOne(final Long id) {
        log.debug("Request to get Product : {}", id);
        final Product product = productRepository.findOne(id);
        final ProductDTO productDTO = productMapper.productToProductDTO(product);
        return productDTO;
    }

    /**
     * Delete the product by id.
     *
     * @param id
     *            the id of the entity
     */
    public void delete(final Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }

    public ProductDTO update(final ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productRepository.findOne(productDTO.getId());
        product = productMapper.update(productDTO, product);
        product.setUpdatedDate(ZonedDateTime.now());
        product = productRepository.save(product);
        final ProductDTO result = productMapper.productToProductDTO(product);
        return result;
    }
}
