package bzh.zomzog.zeshop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Zomzog on 05/06/2017.
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {
    public static void main(final String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


}
