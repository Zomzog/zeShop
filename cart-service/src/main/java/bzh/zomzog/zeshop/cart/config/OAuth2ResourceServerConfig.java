package bzh.zomzog.zeshop.cart.config;

import bzh.zomzog.zeshop.common.configuration.AuthoritiesConstants;
import bzh.zomzog.zeshop.common.configuration.ConfigurationConstants;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final Environment env;

    public OAuth2ResourceServerConfig(final Environment env) {
        this.env = env;
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
                    .antMatchers("/carts/profile-info").permitAll()
                    .antMatchers("/carts/**").authenticated()
                    .antMatchers("/management/health").permitAll()
                    .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .antMatchers("/swagger-resources/configuration/ui").permitAll();
        if(this.env.acceptsProfiles(ConfigurationConstants.SPRING_PROFILE_DEVELOPMENT)){
            http
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll();
        }
        // @formatter:on
    }

    @Bean
    public TokenStore tokenStore(final JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        final Resource resource = new ClassPathResource("public.key");
        String publicKey = null;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
}
