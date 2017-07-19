package bzh.zomzog.zeshop.auth;

import bzh.zomzog.zeshop.auth.configuration.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, AuthProperties.class})
public class AuthServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
