package bzh.zomzog.zeshop.cucumber.stepdefs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bzh.zomzog.zeshop.web.rest.UserResource;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserStepDefs extends StepDefs {

    @Autowired
    private UserResource userResource;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @When("^I search user '(.*)'$")
    public void i_search_user_admin(final String userId) throws Throwable {
        world.actions = restUserMockMvc.perform(get("/api/users/" + userId).accept(MediaType.APPLICATION_JSON));
    }

    @Then("^the user is found$")
    public void the_user_is_found() throws Throwable {
        world.actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Then("^his last name is '(.*)'$")
    public void his_last_name_is(final String lastName) throws Throwable {
        world.actions.andExpect(jsonPath("$.lastName").value(lastName));
    }

}
