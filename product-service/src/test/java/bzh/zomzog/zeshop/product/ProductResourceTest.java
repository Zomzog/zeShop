package bzh.zomzog.zeshop.product;

import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.domain.Product;
import bzh.zomzog.zeshop.product.domain.ProductCustomizationField;
import bzh.zomzog.zeshop.product.domain.enums.ProductCustomizationType;
import bzh.zomzog.zeshop.product.repository.ImageRepository;
import bzh.zomzog.zeshop.product.repository.ProductRepository;
import bzh.zomzog.zeshop.product.service.ProductService;
import bzh.zomzog.zeshop.product.service.StorageService;
import bzh.zomzog.zeshop.product.service.dto.product.ProductCustomizationFieldDTO;
import bzh.zomzog.zeshop.product.service.dto.product.ProductDTO;
import bzh.zomzog.zeshop.product.service.mapper.product.ProductMapper;
import bzh.zomzog.zeshop.product.web.rest.ProductResource;
import bzh.zomzog.zeshop.product.web.rest.error.ExceptionTranslator;
import bzh.zomzog.zeshop.web.rest.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServerApplication.class)
public class ProductResourceTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final int DEFAULT_QUANTITY = 0;
    private static final int UPDATED_QUANTITY = 100;

    private static final Float DEFAULT_PRICE = 0.5f;
    private static final Float UPDATED_PRICE = 120.00f;

    private static final boolean DEFAULT_AVAILABLE = true;
    private static final boolean UPDATED_AVAILABLE = false;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
            ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1489700574L),
            ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1489444445L),
            ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(8888888888L),
            ZoneOffset.UTC);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;
    @MockBean
    private StorageService storageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(this.productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
                .setCustomArgumentResolvers(this.pageableArgumentResolver)
                .setControllerAdvice(this.exceptionTranslator)
                .setMessageConverters(this.jacksonMessageConverter)
                .build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity() {
        final Product product = new Product()//
                .name(DEFAULT_NAME)//
                .description(DEFAULT_DESCRIPTION)//
                .quantity(DEFAULT_QUANTITY)//
                .price(DEFAULT_PRICE)//
                .available(DEFAULT_AVAILABLE)//
                .createdDate(DEFAULT_CREATED_DATE)//
                .updatedDate(DEFAULT_UPDATED_DATE);
        return product;
    }

    @Before
    public void initTest() {
        this.product = createEntity();
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        final int databaseSizeBeforeCreate = this.productRepository.findAll().size();
        final ZonedDateTime now = ZonedDateTime.now();
        // Create the Product
        final ProductDTO productDTO = this.productMapper.productToProductDTO(this.product);
        this.restProductMockMvc.perform(post("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isCreated());

        // Validate the Product in the database
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        final Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testProduct.getCreatedDate()).isNotEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getCreatedDate()).isAfter(now);
        assertThat(testProduct.getUpdatedDate()).isNull();
    }

    @Test
    @Transactional
    public void createProductWithCustomisation() throws Exception {
        final int databaseSizeBeforeCreate = this.productRepository.findAll().size();
        final ZonedDateTime now = ZonedDateTime.now();
        // Create the Product
        final ProductDTO productDTO = this.productMapper.productToProductDTO(this.product);
        final ProductCustomizationFieldDTO pcf1 = new ProductCustomizationFieldDTO();
        pcf1.setName("PonyText");
        pcf1.setType(ProductCustomizationType.TEXT);
        final ProductCustomizationFieldDTO pcf2 = new ProductCustomizationFieldDTO();
        pcf2.setName("PonyBoolean");
        pcf2.setType(ProductCustomizationType.BOOLEAN);
        final ProductCustomizationFieldDTO pcf3 = new ProductCustomizationFieldDTO();
        pcf3.setName("PonyCustom");
        pcf3.setType(ProductCustomizationType.CUSTOM);
        productDTO.getCustomizationFields().addAll(Arrays.asList(pcf1, pcf2, pcf3));

        this.restProductMockMvc.perform(post("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isCreated());

        // Validate the Product in the database
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        final Product testProduct = productList.get(productList.size() - 1);
        final Set<ProductCustomizationField> custom = testProduct.getCustomizationFields();
        assertThat(custom).hasSize(3);
        assertThat(custom.stream()
                .filter(pcf -> pcf.getName().equals(pcf1.getName()) && pcf.getType().equals(pcf1.getType()))
                .collect(Collectors.toList())).hasSize(1);
        assertThat(custom.stream()
                .filter(pcf -> pcf.getName().equals(pcf2.getName()) && pcf.getType().equals(pcf2.getType()))
                .collect(Collectors.toList())).hasSize(1);
        assertThat(custom.stream()
                .filter(pcf -> pcf.getName().equals(pcf3.getName()) && pcf.getType().equals(pcf3.getType()))
                .collect(Collectors.toList())).hasSize(1);

    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        final int databaseSizeBeforeCreate = this.productRepository.findAll().size();

        // Create the Product with an existing ID
        this.product.setId(1L);
        final ProductDTO productDTO = this.productMapper.productToProductDTO(this.product);

        // An entity with an existing ID cannot be created, so this API call
        // must fail
        this.restProductMockMvc.perform(post("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isBadRequest());

        // Validate the Alice in the database
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        final int databaseSizeBeforeTest = this.productRepository.findAll().size();
        // set the field null
        this.product.setName(null);

        // Create the Product, which fails.
        final ProductDTO productDTO = this.productMapper.productToProductDTO(this.product);

        this.restProductMockMvc.perform(post("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isBadRequest());

        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        this.productRepository.saveAndFlush(this.product);

        // Get all the productList
        this.restProductMockMvc.perform(get("/products?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(this.product.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        this.productRepository.saveAndFlush(this.product);

        // Get the product
        this.restProductMockMvc.perform(get("/products/{id}", this.product.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(this.product.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
                .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
                .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
                .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        this.restProductMockMvc.perform(get("/products/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void updateProduct() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        // Initialize the database
        this.productRepository.saveAndFlush(this.product);
        final int databaseSizeBeforeUpdate = this.productRepository.findAll().size();

        // Update the product
        final Product updatedProduct = this.productRepository.findOneWithEagerRelationships(this.product.getId());
        updatedProduct//
                .name(UPDATED_NAME)//
                .description(UPDATED_DESCRIPTION)//
                .quantity(UPDATED_QUANTITY)//
                .price(UPDATED_PRICE)//
                .available(UPDATED_AVAILABLE)//
                .createdDate(UPDATED_CREATED_DATE)//
                .updatedDate(UPDATED_UPDATED_DATE);

        final ProductDTO productDTO = this.productMapper.productToProductDTO(updatedProduct);

        this.restProductMockMvc.perform(put("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isOk());

        // Validate the Product in the database
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        final Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testProduct.getCreatedDate()).isNotEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getUpdatedDate()).isNotEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testProduct.getUpdatedDate()).isAfter(now);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        final int databaseSizeBeforeUpdate = this.productRepository.findAll().size();

        // Create the Product
        final ProductDTO productDTO = this.productMapper.productToProductDTO(this.product);

        // If the entity doesn't have an ID, it will be created instead of just
        // being updated
        this.restProductMockMvc.perform(put("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO))).andExpect(status().isCreated());

        // Validate the Product in the database
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        this.productRepository.saveAndFlush(this.product);
        final int databaseSizeBeforeDelete = this.productRepository.findAll().size();

        // Delete the product
        this.restProductMockMvc
                .perform(delete("/products/{id}", this.product.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
    }

    @Test
    public void addImages() throws Exception {
        // Initialize the database
        this.product = this.productRepository.saveAndFlush(this.product);
        final String filename = "test.txt";

        final MockMultipartFile multipartFile =
                new MockMultipartFile("file", filename, "text/plain", "Spring Framework".getBytes());

        // Get the product
        this.restProductMockMvc.perform(fileUpload("/products/{id}/images", this.product.getId()).file(multipartFile))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(not(filename)))
                .andExpect(jsonPath("$.productId").isNotEmpty());

        final Product last = this.productRepository.findOneWithEagerRelationships(this.product.getId());
        assertThat(last.getImages().size()).isEqualTo(1);
        final Image image = last.getImages().iterator().next();
        assertThat(image.getId()).isNotNull();
        assertThat(image.getName()).isNotNull();
        assertThat(image.getName()).isNotEqualTo(multipartFile.getName());
        assertThat(image.getName()).isNotEqualTo(multipartFile.getOriginalFilename());

        then(this.storageService).should().store(eq(multipartFile), anyString());
    }

    @Test
    public void addImagesToNonExistingProduct() throws Exception {
        // Initialize the database
        final String filename = "test.txt";
        final long imageCountBeforeUpdate = this.imageRepository.count();

        final MockMultipartFile multipartFile =
                new MockMultipartFile("file", filename, "text/plain", "Spring Framework".getBytes());

        // Get the product
        this.restProductMockMvc.perform(fileUpload("/products/{id}/images", Integer.MAX_VALUE).file(multipartFile))
                .andExpect(status().isBadRequest());


        assertThat(this.imageRepository.count()).isEqualTo(imageCountBeforeUpdate);

        verify(this.storageService, never()).store(anyObject(), anyString());
    }

    @Test
    public void updateWithOneImageRemoved() throws Exception {
        // Initialize the database
        final Image image1 = new Image().name("image1").product(this.product);
        final Image image2 = new Image().name("image2").product(this.product);
        this.product.getImages().add(image1);
        this.product.getImages().add(image2);
        this.product = this.productRepository.saveAndFlush(this.product);
        final long imageCountBeforeUpdate = this.imageRepository.count();

        // Create the Product
        this.product.getImages().removeIf(i -> i.getName().equals("image1"));
        final ProductDTO productDTO = this.productMapper.productToProductDTO(this.product);

        // If the entity doesn't have an ID, it will be created instead of just
        // being updated
        this.restProductMockMvc.perform(put("/products").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(this.product.getId().intValue()))
                .andExpect(jsonPath("$.images").value(hasSize(1)))
                .andExpect(jsonPath("$.images.[*].name").value(hasItem("image2")));

        assertThat(this.imageRepository.count()).isEqualTo(imageCountBeforeUpdate - 1);
    }
}

