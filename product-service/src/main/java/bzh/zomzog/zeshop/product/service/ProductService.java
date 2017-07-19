package bzh.zomzog.zeshop.product.service;

import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.domain.Product;
import bzh.zomzog.zeshop.product.domain.ProductCustomizationField;
import bzh.zomzog.zeshop.product.exception.StorageException;
import bzh.zomzog.zeshop.product.repository.ImageRepository;
import bzh.zomzog.zeshop.product.repository.ProductRepository;
import bzh.zomzog.zeshop.product.service.dto.ImageDTO;
import bzh.zomzog.zeshop.product.service.dto.product.ProductDTO;
import bzh.zomzog.zeshop.product.service.mapper.ImageMapper;
import bzh.zomzog.zeshop.product.service.mapper.product.ProductMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing Products.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private static final int IMAGE_NAME_SIZE = 20;

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    private final ProductMapper productMapper;
    private final ImageMapper imageMapper;

    private final StorageService storageService;

    public ProductService(final ProductRepository productRepository, final ImageRepository imageRepository, final ProductMapper productMapper, final ImageMapper imageMapper, final StorageService storageService) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.productMapper = productMapper;
        this.imageMapper = imageMapper;
        this.storageService = storageService;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
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
     * @param pageable the pagination information
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
     * @param id the id of the entity
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
     * @param id the id of the entity
     */
    public void delete(final Long id) {
        this.log.debug("Request to delete Product : {}", id);
        this.productRepository.delete(id);
    }

    /**
     * Update a product.
     *
     * @param productDTO the entity to save
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
     * @param productId product's id
     * @param file      file
     * @return image informations
     * @throws StorageException
     */
    public ImageDTO addImageToProduct(final Long productId, final MultipartFile file) throws StorageException, BadParameterException {
        this.log.debug("Request to add image to product : {}", productId);
        final Product product = this.productRepository.findOne(productId);
        if (null == product) {
            throw new BadParameterException("product", "id", productId.toString());
        }
        final String imageName = RandomStringUtils.randomAlphabetic(IMAGE_NAME_SIZE);
        this.storageService.store(file, imageName);
        // FIXME move this to ImageService
        final Image image = new Image().name(imageName).product(product);
        product.getImages().add(image);
        this.imageRepository.save(image);
        return this.imageMapper.imageToImageDTO(image);
    }

    /**
     * TODO : most efficient way with mapstruct ?
     * <p>
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
