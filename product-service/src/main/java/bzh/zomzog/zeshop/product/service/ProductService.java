package bzh.zomzog.zeshop.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.product.domain.Product;
import bzh.zomzog.zeshop.product.domain.ProductCustomizationField;
import bzh.zomzog.zeshop.product.repository.ProductRepository;
import bzh.zomzog.zeshop.product.service.dto.product.ProductDTO;
import bzh.zomzog.zeshop.product.service.mapper.product.ProductMapper;

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
        this.log.debug("Request to save Product : {}", productDTO);
        Product product = this.productMapper.productDTOToProduct(productDTO);
        updateSubRef(product);
        product = this.productRepository.save(product);
        final ProductDTO result = this.productMapper.productToProductDTO(product);
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
        this.log.debug("Request to get all Products");
        final Page<Product> result = this.productRepository.findAll(pageable);
        return result.map(product -> this.productMapper.productToProductDTO(product));
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
        this.log.debug("Request to get Product : {}", id);
        final Product product = this.productRepository.findOne(id);
        final ProductDTO productDTO = this.productMapper.productToProductDTO(product);
        return productDTO;
    }

    /**
     * Delete the product by id.
     *
     * @param id
     *            the id of the entity
     */
    public void delete(final Long id) {
        this.log.debug("Request to delete Product : {}", id);
        this.productRepository.delete(id);
    }

    /**
     * Update a product.
     *
     * @param productDTO
     *            the entity to save
     * @return the persisted entity
     */
    public ProductDTO update(final ProductDTO productDTO) {
        this.log.debug("Request to save Product : {}", productDTO);
        Product product = this.productRepository.findOne(productDTO.getId());
        product = this.productMapper.update(productDTO, product);
        updateSubRef(product);
        product = this.productRepository.save(product);
        final ProductDTO result = this.productMapper.productToProductDTO(product);
        return result;
    }

    /**
     * TODO : most efficient way with mapstruct ?
     * 
     * Update childs elements for hibernate mapping
     * 
     * @param product
     * @return
     */
    private Product updateSubRef(final Product product) {
        for (final ProductCustomizationField pcf : product.getCustomizationFields()) {
            pcf.setProduct(product);
        }
        return product;
    }

}
