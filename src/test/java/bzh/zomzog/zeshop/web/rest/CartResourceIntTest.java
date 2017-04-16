package bzh.zomzog.zeshop.web.rest;

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
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.After;
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

import bzh.zomzog.zeshop.ZeShopApp;
import bzh.zomzog.zeshop.domain.cart.Cart;
import bzh.zomzog.zeshop.domain.cart.CartProduct;
import bzh.zomzog.zeshop.domain.product.Product;
import bzh.zomzog.zeshop.repository.CartRepository;
import bzh.zomzog.zeshop.repository.ProductRepository;
import bzh.zomzog.zeshop.service.CartService;
import bzh.zomzog.zeshop.service.dto.cart.CartDTO;
import bzh.zomzog.zeshop.service.mapper.cart.CartMapper;
import bzh.zomzog.zeshop.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the CartResource REST controller.
 *
 * @see CartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZeShopApp.class)
public class CartResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
            ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1489700574L),
            ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1489444445L),
            ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(8888888888L),
            ZoneOffset.UTC);

    @Autowired
    private CartRepository    cartRepository;
    @Autowired
    private ProductRepository productRepository;

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

    private Product product;

    private CartProduct cartProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartResource cartResource = new CartResource(cartService);
        restCartMockMvc = MockMvcBuilders.standaloneSetup(cartResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
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
        product = productRepository.saveAndFlush(ProductResourceIntTest.createEntity(em));
        cart = createEntity(em);
        cartProduct = new CartProduct().product(product).quantity(5L).cart(cart);
        cart.getProducts().add(cartProduct);
    }

    @After
    public void theardown() {
        Optional.ofNullable(product.getId()).ifPresent(productRepository::delete);
    }

    @Test
    @Transactional
    public void addToCart() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        // Initialize the database
        cartRepository.saveAndFlush(cart);
        final int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart

        restCartMockMvc.perform(put("/api/carts/{cartId}/product/{productId}", cart.getId(), product.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

        // Validate the Cart in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        final Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getUpdatedDate()).isAfter(now);

        // Teardown
        cartRepository.delete(cart.getId());
    }

    @Test
    @Transactional
    public void addToCartNotExistingProduct() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);
        final int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart

        restCartMockMvc.perform(put("/api/carts/{cartId}/product/{productId}", cart.getId(), Integer.MAX_VALUE)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest());

        // Validate the Cart in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        final Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getUpdatedDate()).isEqualTo(cart.getUpdatedDate());
        // Teardown
        cartRepository.delete(cart.getId());
    }

    @Test
    @Transactional
    public void createCart() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        final int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // Create the Cart
        final CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        restCartMockMvc.perform(post("/api/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isCreated());

        // Validate the Cart in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        final Cart testCart = cartList.get(cartList.size() - 1);
        // Created and updated date are managed on server side
        assertThat(testCart.getCreatedDate()).isNotEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCart.getCreatedDate()).isAfter(now);
        assertThat(testCart.getUpdatedDate()).isAfter(now);
        assertThat(testCart.getProducts()).hasSize(1);
        // Teardown
        cartRepository.delete(testCart.getId());
    }

    @Test
    @Transactional
    public void createCartWithProductWithNoCart() throws Exception {
        final int databaseSizeBeforeCreate = cartRepository.findAll().size();
        cartProduct.setCart(null);

        // Create the Cart
        final CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        restCartMockMvc.perform(post("/api/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isCreated());

        // Validate the Cart in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        final Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getProducts()).hasSize(1);
        // Teardown
        cartRepository.delete(testCart.getId());
    }

    @Test
    @Transactional
    public void createCartWithExistingId() throws Exception {
        // Create the Cart with an existing ID
        cartRepository.saveAndFlush(cart);

        final int databaseSizeBeforeCreate = cartRepository.findAll().size();

        final CartDTO cartDTO = cartMapper.cartToCartDTO(cart);

        // An entity with an existing ID cannot be created, so this API call
        // must fail
        restCartMockMvc.perform(post("/api/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isBadRequest());

        // Validate the Alice in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
        // Teardown
        cartRepository.delete(cart.getId());
    }

    @Test
    @Transactional
    public void getAllCarts() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList
        restCartMockMvc.perform(get("/api/carts?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().intValue())));
        // Teardown
        cartRepository.delete(cart.getId());
    }

    @Test
    @Transactional
    public void getCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get the cart
        restCartMockMvc.perform(get("/api/carts/{id}", cart.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(cart.getId().intValue()));
        // Teardown
        cartRepository.delete(cart.getId());
    }

    @Test
    @Transactional
    public void getNonExistingCart() throws Exception {
        // Get the cart
        restCartMockMvc.perform(get("/api/carts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void updateCart() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        productRepository.saveAndFlush(product);
        final int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        final Cart updatedCart = cartRepository.findOneWithEagerRelationships(cart.getId());
        updatedCart.createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        updatedCart.getProducts().add(new CartProduct().product(product).quantity(1L));
        final CartDTO cartDTO = cartMapper.cartToCartDTO(updatedCart);

        restCartMockMvc.perform(put("/api/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isOk());

        // Validate the Cart in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        final Cart testCart = cartRepository.findOneWithEagerRelationships(updatedCart.getId());
        // Updated and created date are managed on server side
        assertThat(testCart.getProducts().size()).isEqualTo(2);
        assertThat(testCart.getCreatedDate()).isNotEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCart.getUpdatedDate()).isNotEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCart.getUpdatedDate()).isAfter(now);
        // Teardown
        cartRepository.delete(cart.getId());
    }

    @Test
    @Transactional
    public void updateNonExistingCart() throws Exception {
        final int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Create the Cart
        final CartDTO cartDTO = cartMapper.cartToCartDTO(cart);

        // If the entity doesn't have an ID, it will be created instead of just
        // being updated
        restCartMockMvc.perform(put("/api/carts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO))).andExpect(status().isCreated());

        // Validate the Cart in the database
        final List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);
        final long databaseSizeBeforeDelete = cartRepository.count();
        final long databaseProductSizeBeforeDelete = productRepository.count();

        // Get the cart
        restCartMockMvc.perform(delete("/api/carts/{id}", cart.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        assertThat(cartRepository.count()).isEqualTo(databaseSizeBeforeDelete - 1);

        // Validate the porudct database is untouched
        assertThat(productRepository.count()).isEqualTo(databaseProductSizeBeforeDelete);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cart.class);
    }
}
