package bzh.zomzog.zeshop.cart.config;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
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
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerSpringMvcPlugin() throws IOException, XmlPullParserException {

        final MavenXpp3Reader reader = new MavenXpp3Reader();
        final Model model = reader.read(new FileReader("pom.xml"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("bzh.zomzog.zeshop"))
                .paths(PathSelectors.any())
                .build().apiInfo
                        (new ApiInfo(model.getName(),
                                model.getDescription(),
                                model.getParent().getVersion(),
                                null,
                                new Contact("Zomzog", "", "zomzog@zomzog.fr"),
                                null,
                                null));
    }
}
