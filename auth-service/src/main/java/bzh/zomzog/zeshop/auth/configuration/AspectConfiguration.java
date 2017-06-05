package bzh.zomzog.zeshop.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import bzh.zomzog.zeshop.auth.aop.logging.LoggingAspect;
import bzh.zomzog.zeshop.configuration.ConfigurationConstants;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Bean
    @Profile(ConfigurationConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(final Environment env) {
        return new LoggingAspect(env);
    }
}
