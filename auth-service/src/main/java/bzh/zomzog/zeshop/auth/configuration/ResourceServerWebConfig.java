package bzh.zomzog.zeshop.auth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// FIXME must be removed when oauth != account service
@Configuration
@EnableWebMvc
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
    //
}
