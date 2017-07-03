package bzh.zomzog.zeshop.product;

import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.domain.Product;
import bzh.zomzog.zeshop.product.exception.StorageFileNotFoundException;
import bzh.zomzog.zeshop.product.repository.ImageRepository;
import bzh.zomzog.zeshop.product.repository.ProductRepository;
import bzh.zomzog.zeshop.product.service.ImageService;
import bzh.zomzog.zeshop.product.service.ProductService;
import bzh.zomzog.zeshop.product.service.StorageService;
import bzh.zomzog.zeshop.product.web.rest.ImageResource;
import bzh.zomzog.zeshop.product.web.rest.ProductResource;
import bzh.zomzog.zeshop.product.web.rest.error.ExceptionTranslator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Zomzog on 01/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServerApplication.class)
public class ImageResourceTest {

    @Autowired
    private ImageRepository imageRepository;
    // TODO remove product dependency
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;
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

    private Image image;
    private Product product;

    private MockMvc mockMvcImage;
    private MockMvc mockMvcProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageResource imageResource = new ImageResource(this.imageService);
        this.mockMvcImage = MockMvcBuilders.standaloneSetup(imageResource)
                .setCustomArgumentResolvers(this.pageableArgumentResolver)
                .setControllerAdvice(this.exceptionTranslator)
                //.setMessageConverters(this.jacksonMessageConverter)
                .build();
        final ProductResource productResource = new ProductResource(this.productService);
        this.mockMvcProduct = MockMvcBuilders.standaloneSetup(productResource)
                .setCustomArgumentResolvers(this.pageableArgumentResolver)
                .setControllerAdvice(this.exceptionTranslator)
                //.setMessageConverters(this.jacksonMessageConverter)
                .build();
    }

    @Before
    public void init() {
        this.product = this.productRepository.save(ProductResourceTest.createEntity());

        this.image = new Image()
                .name("name")
                .product(this.product);
    }

    @After
    public void teardown() {
        this.productRepository.delete(this.product.getId());
    }

    @Test
    public void shouldUploadFile() throws Exception {
        final MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());

        final MvcResult result = this.mockMvcProduct.perform(fileUpload("/products/{id}/images", this.product.getId()).file(multipartFile))
                .andExpect(status().isCreated())
                .andReturn();

        final MockHttpServletResponse response = result.getResponse();

        assertThat(response.getHeader(HttpHeaders.LOCATION))
                .matches("/api/products/4/images/[0-9]*");

        then(this.storageService).should().store(any(MultipartFile.class), anyString());
    }

    @Test
    public void shouldDownloadFile() throws Exception {
        this.image = this.imageRepository.save(this.image);

        final ClassPathResource resource = new ClassPathResource("name", getClass());
        given(this.storageService.loadAsResource("name")).willReturn(resource);

        final MvcResult result = this.mockMvcImage.perform(get("/images/{id}", this.image.getId()))
                .andExpect(status().isOk())
                .andReturn();
        final MockHttpServletResponse response = result.getResponse();
        assertThat(response.getHeader(HttpHeaders.CONTENT_DISPOSITION))
                .isEqualTo("attachment; filename=\"name\"");
        assertThat(response.getContentAsString()).isEqualTo("Spring Framework");
        // Teardown
        this.imageRepository.delete(this.image.getId());
    }

    @Test
    public void should404WhenMissingFile() throws Exception {
        this.image = this.imageRepository.save(this.image);

        given(this.storageService.loadAsResource(this.image.getName()))
                .willThrow(StorageFileNotFoundException.class);

        this.mockMvcImage.perform(get("/images/{id}", this.image.getId()))
                .andExpect(status().isNotFound());
        // Teardown
        this.imageRepository.delete(this.image.getId());
    }

    @Test
    public void should404WhenIdNotFoundFile() throws Exception {
        given(this.storageService.loadAsResource(this.image.getName()))
                .willThrow(StorageFileNotFoundException.class);

        this.mockMvcImage.perform(get("/images/{id}", Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

}
