package bzh.zomzog.zeshop.product.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import bzh.zomzog.zeshop.product.ProductServerApplication;
import bzh.zomzog.zeshop.product.domain.Product;
import bzh.zomzog.zeshop.product.domain.ProductCustomizationField;
import bzh.zomzog.zeshop.product.domain.enums.ProductCustomizationType;
import bzh.zomzog.zeshop.product.repository.ProductRepository;
import bzh.zomzog.zeshop.product.service.ProductService;
import bzh.zomzog.zeshop.product.service.dto.product.ProductCustomizationFieldDTO;
import bzh.zomzog.zeshop.product.service.dto.product.ProductDTO;
import bzh.zomzog.zeshop.product.service.mapper.product.ProductMapper;
import bzh.zomzog.zeshop.product.web.rest.ProductResource;
import bzh.zomzog.zeshop.product.web.rest.error.ExceptionTranslator;
import bzh.zomzog.zeshop.web.rest.TestUtil;

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
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(this.productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
                .setCustomArgumentResolvers(this.pageableArgumentResolver).setControllerAdvice(this.exceptionTranslator)
                .setMessageConverters(this.jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(final EntityManager em) {
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
        this.product = createEntity(this.em);
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

        // Get the product
        this.restProductMockMvc
                .perform(delete("/products/{id}", this.product.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        final List<Product> productList = this.productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
    }
}
