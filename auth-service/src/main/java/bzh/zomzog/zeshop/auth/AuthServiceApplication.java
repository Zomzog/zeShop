package bzh.zomzog.zeshop.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
public class AuthServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
