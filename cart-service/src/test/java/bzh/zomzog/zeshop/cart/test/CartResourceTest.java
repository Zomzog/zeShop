package bzh.zomzog.zeshop.cart.test;

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
import java.util.List;

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

import bzh.zomzog.zeshop.cart.CartServerApplication;
import bzh.zomzog.zeshop.cart.domain.cart.Cart;
import bzh.zomzog.zeshop.cart.domain.cart.CartProduct;
import bzh.zomzog.zeshop.cart.repository.CartRepository;
import bzh.zomzog.zeshop.cart.service.CartService;
import bzh.zomzog.zeshop.cart.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.cart.service.mapper.cart.CartMapper;
import bzh.zomzog.zeshop.cart.web.rest.CartResource;
import bzh.zomzog.zeshop.cart.web.rest.error.ExceptionTranslator;
import bzh.zomzog.zeshop.web.rest.TestUtil;

/**
 * Test class for the CartResource REST controller.
 *
 * @see CartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartServerApplication.class)
public class CartResourceTest {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
            ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1489700574L),
            ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1489444445L),
            ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(8888888888L),
            ZoneOffset.UTC);

    private static final Long DEFAULT_PRODUCT_ID = 1L;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCartMockMvc;

    private Cart cart;

    private CartProduct cartProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartResource cartResource = new CartResource(this.cartService);
        this.restCartMockMvc = MockMvcBuilders.standaloneSetup(cartResource)
                .setCustomArgumentResolvers(this.pageableArgumentResolver).setControllerAdvice(this.exceptionTranslator)
                .setMessageConverters(this.jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createEntity(final EntityManager em) {
        final Cart cart = new Cart().createdDate(DEFAULT_CREATED_DATE).updatedDate(DEFAULT_UPDATED_DATE);
        return cart;
    }

    @Before
    public void initTest() {
        this.cart = createEntity(this.em);
        this.cartProduct = new CartProduct().productId(DEFAULT_PRODUCT_ID).quantity(5L).cart(this.cart);
        this.cart.getProducts().add(this.cartProduct);
    }

    @Test
    @Transactional
    public void addToCart() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        // Initialize the database
        this.cartRepository.saveAndFlush(this.cart);
        final int databaseSizeBeforeUpdate = this.cartRepository.findAll().size();

        // Update the cart

        this.restCartMockMvc
                .perform(put("/carts/{cartId}/product/{productId}", this.cart.getId(), DEFAULT_PRODUCT_ID)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the Cart in the database
        final List<Cart> cartList = this.cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        final Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getUpdatedDate()).isAfter(now);

        // Teardown
        this.cartRepository.delete(this.cart.getId());
    }

    // FIXME Restore the test
    // @Test
    // @Transactional
    // public void addToCartNotExistingProduct() throws Exception {
    // // Initialize the database
    // this.cartRepository.saveAndFlush(this.cart);
    // final int databaseSizeBeforeUpdate =
    // this.cartRepository.findAll().size();
    //
    // // Update the cart
    //
    // this.restCartMockMvc
    // .perform(put("/carts/{cartId}/product/{productId}", this.cart.getId(),
    // Integer.MAX_VALUE)
    // .contentType(TestUtil.APPLICATION_JSON_UTF8))
    // .andExpect(status().isBadRequest());
    //
    // // Validate the Cart in the database
    // final List<Cart> cartList = this.cartRepository.findAll();
    // assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    // final Cart testCart = cartList.get(cartList.size() - 1);
    // assertThat(testCart.getUpdatedDate()).isEqualTo(this.cart.getUpdatedDate());
    // // Teardown
    // this.cartRepository.delete(this.cart.getId());
    // }

    @Test
    @Transactional
    public void createCart() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        final int databaseSizeBeforeCreate = this.cartRepository.findAll().size();

        // Create the Cart
        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(this.cart);
        this.restCartMockMvc.perform(post("/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isCreated());

        // Validate the Cart in the database
        final List<Cart> cartList = this.cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        final Cart testCart = cartList.get(cartList.size() - 1);
        // Created and updated date are managed on server side
        assertThat(testCart.getCreatedDate()).isNotEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCart.getCreatedDate()).isAfter(now);
        assertThat(testCart.getUpdatedDate()).isAfter(now);
        assertThat(testCart.getProducts()).hasSize(1);
        // Teardown
        this.cartRepository.delete(testCart.getId());
    }

    @Test
    @Transactional
    public void createCartWithProductWithNoCart() throws Exception {
        final int databaseSizeBeforeCreate = this.cartRepository.findAll().size();
        this.cartProduct.setCart(null);

        // Create the Cart
        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(this.cart);
        this.restCartMockMvc.perform(post("/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isCreated());

        // Validate the Cart in the database
        final List<Cart> cartList = this.cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        final Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getProducts()).hasSize(1);
        // Teardown
        this.cartRepository.delete(testCart.getId());
    }

    @Test
    @Transactional
    public void createCartWithExistingId() throws Exception {
        // Create the Cart with an existing ID
        this.cartRepository.saveAndFlush(this.cart);

        final int databaseSizeBeforeCreate = this.cartRepository.findAll().size();

        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(this.cart);

        // An entity with an existing ID cannot be created, so this API call
        // must fail
        this.restCartMockMvc.perform(post("/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isBadRequest());

        // Validate the Alice in the database
        final List<Cart> cartList = this.cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
        // Teardown
        this.cartRepository.delete(this.cart.getId());
    }

    @Test
    @Transactional
    public void getAllCarts() throws Exception {
        // Initialize the database
        this.cartRepository.saveAndFlush(this.cart);

        // Get all the cartList
        this.restCartMockMvc.perform(get("/carts?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(this.cart.getId().intValue())));
        // Teardown
        this.cartRepository.delete(this.cart.getId());
    }

    @Test
    @Transactional
    public void getCart() throws Exception {
        // Initialize the database
        this.cartRepository.saveAndFlush(this.cart);

        // Get the cart
        this.restCartMockMvc.perform(get("/carts/{id}", this.cart.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(this.cart.getId().intValue()));
        // Teardown
        this.cartRepository.delete(this.cart.getId());
    }

    @Test
    @Transactional
    public void getNonExistingCart() throws Exception {
        // Get the cart
        this.restCartMockMvc.perform(get("/carts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void updateCart() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        // Initialize the database
        this.cartRepository.saveAndFlush(this.cart);

        final int databaseSizeBeforeUpdate = this.cartRepository.findAll().size();

        // Update the cart
        final Cart updatedCart = this.cartRepository.findOneWithEagerRelationships(this.cart.getId());
        updatedCart.createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        updatedCart.getProducts().add(new CartProduct().productId(DEFAULT_PRODUCT_ID).quantity(1L));
        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(updatedCart);

        this.restCartMockMvc.perform(put("/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isOk());

        // Validate the Cart in the database
        final List<Cart> cartList = this.cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        final Cart testCart = this.cartRepository.findOneWithEagerRelationships(updatedCart.getId());
        // Updated and created date are managed on server side
        assertThat(testCart.getProducts().size()).isEqualTo(2);
        assertThat(testCart.getCreatedDate()).isNotEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCart.getUpdatedDate()).isNotEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCart.getUpdatedDate()).isAfter(now);
        // Teardown
        this.cartRepository.delete(this.cart.getId());
    }

    @Test
    @Transactional
    public void updateNonExistingCart() throws Exception {
        final int databaseSizeBeforeUpdate = this.cartRepository.findAll().size();

        // Create the Cart
        final CartDTO cartDTO = this.cartMapper.cartToCartDTO(this.cart);

        // If the entity doesn't have an ID, it will be created instead of just
        // being updated
        this.restCartMockMvc.perform(put("/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isCreated());

        // Validate the Cart in the database
        final List<Cart> cartList = this.cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCart() throws Exception {
        // Initialize the database
        this.cartRepository.saveAndFlush(this.cart);
        final long databaseSizeBeforeDelete = this.cartRepository.count();

        // Get the cart
        this.restCartMockMvc
                .perform(delete("/carts/{id}", this.cart.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        assertThat(this.cartRepository.count()).isEqualTo(databaseSizeBeforeDelete - 1);

    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cart.class);
    }
}
