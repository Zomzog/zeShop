package bzh.zomzog.zeshop.auth.configuration;

import bzh.zomzog.zeshop.configuration.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public OAuth2ResourceServerConfig() {
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .csrf()
                    .disable()
                .headers()
                    .frameOptions()
                    .disable()

                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                    .antMatchers("/account/**").permitAll()
                    // .antMatchers("/accounts/**").authenticated()
                    .antMatchers("/accounts").authenticated()
                    .antMatchers("/management/health").permitAll()
                    .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .antMatchers("/swagger-resources/configuration/ui").permitAll()
                    // FIXEME Remove that
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/list/**").permitAll();
        // @formatter:on
    }
}

