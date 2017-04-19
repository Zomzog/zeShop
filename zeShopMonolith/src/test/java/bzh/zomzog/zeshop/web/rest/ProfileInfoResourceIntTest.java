package bzh.zomzog.zeshop.web.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bzh.zomzog.zeshop.ZeShopApp;
import io.github.jhipster.config.JHipsterProperties;

/**
 * Test class for the ProfileInfoResource REST controller.
 *
 * @see ProfileInfoResource
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZeShopApp.class)
public class ProfileInfoResourceIntTest {

    @Mock
    private Environment environment;

    @Mock
    private JHipsterProperties jHipsterProperties;

    private MockMvc restProfileMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final String mockProfile[] = { "test" };
        final JHipsterProperties.Ribbon ribbon = new JHipsterProperties.Ribbon();
        ribbon.setDisplayOnActiveProfiles(mockProfile);
        when(jHipsterProperties.getRibbon()).thenReturn(ribbon);

        final String activeProfiles[] = { "test" };
        when(environment.getDefaultProfiles()).thenReturn(activeProfiles);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);

        final ProfileInfoResource profileInfoResource = new ProfileInfoResource(environment, jHipsterProperties);
        restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileInfoResource).build();
    }

    @Test
    public void getProfileInfoWithRibbon() throws Exception {
        restProfileMockMvc.perform(get("/api/profile-info")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileInfoWithoutRibbon() throws Exception {
        final JHipsterProperties.Ribbon ribbon = new JHipsterProperties.Ribbon();
        ribbon.setDisplayOnActiveProfiles(null);
        when(jHipsterProperties.getRibbon()).thenReturn(ribbon);

        restProfileMockMvc.perform(get("/api/profile-info")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileInfoWithoutActiveProfiles() throws Exception {
        final String emptyProfile[] = {};
        when(environment.getDefaultProfiles()).thenReturn(emptyProfile);
        when(environment.getActiveProfiles()).thenReturn(emptyProfile);

        restProfileMockMvc.perform(get("/api/profile-info")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
