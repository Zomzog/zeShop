package bzh.zomzog.zeshop.common.configuration;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Zomzog on 22/07/2017.
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerConfiguration {

    private final SwaggerProperties swaggerProperties;

    public SwaggerConfiguration(final SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() throws IOException, XmlPullParserException {
        final SwaggerProperties.Api api = this.swaggerProperties.getApi();
        final SwaggerProperties.Contact contact = this.swaggerProperties.getContact();
        final MavenXpp3Reader reader = new MavenXpp3Reader();
        final Model model = reader.read(new FileReader("pom.xml"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build().apiInfo
                        (new ApiInfo(api.getName(),
                                api.getDescription(),
                                api.getVersion(),
                                null,
                                new Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
                                null,
                                null));
    }
}
