package bzh.zomzog.zeshop.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({ "bzh.zomzog.zeshop.product.web.controller" })
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
    //
}
