package bzh.zomzog.zeshop.cucumber.stepdefs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bzh.zomzog.zeshop.web.rest.ProductResource;
import bzh.zomzog.zeshop.web.rest.errors.ExceptionTranslator;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProductSepDefs extends StepDefs {

    @Autowired
    private ProductResource productResource;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        restUserMockMvc = MockMvcBuilders.standaloneSetup(productResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @When("^I search product by id '(.*)'$")
    public void i_search_product_by_id(final String productId) throws Throwable {
        world.actions = restUserMockMvc.perform(get("/api/products/" + productId).accept(MediaType.APPLICATION_JSON));
    }

    @When("^I search all products$")
    public void i_search_all_products() throws Throwable {
        world.actions = restUserMockMvc.perform(get("/api/products/").accept(MediaType.APPLICATION_JSON));
    }

    @Then("^his title is '(.*)'$")
    public void his_name_is(final String name) throws Throwable {
        world.actions.andExpect(jsonPath("$.name").value(name));
    }

}
