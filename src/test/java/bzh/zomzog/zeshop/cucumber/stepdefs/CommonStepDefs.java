package bzh.zomzog.zeshop.cucumber.stepdefs;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import cucumber.api.java.en.Then;

/**
 * Common step definitions
 * 
 * @author Zomzog
 *
 */
public class CommonStepDefs extends StepDefs {

    @Then("^the request is successful$")
    public void request_successful() throws Throwable {
        world.actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Then("^get bad request error$")
    public void bad_request() throws Throwable {
        world.actions.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Then("^result contains (\\d) items$")
    public void his_name_is(final int count) throws Throwable {
        world.actions.andExpect(jsonPath("$.length()").value(count));
    }
}
