package bzh.zomzog.zeshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zomzog on 24/07/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }

    @Component
    @Primary
    public class CustomSwaggerResourcesProvider implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            final List<SwaggerResource> resources = new ArrayList<>();
            //resources.add(swaggerResource("account-service", "/api/account/v2/api-docs", "2.0"));
            resources.add(swaggerResource("cart-service", "/api/cart-service/v2/api-docs", "2.0"));
            resources.add(swaggerResource("product-service", "/api/product-service/v2/api-docs", "2.0"));
            //resources.add(swaggerResource("transfer-service", "/api/transfer/v2/api-docs", "2.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(final String name, final String location, final String version) {
            final SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }


    }
}