package bzh.zomzog.zeshop.common.configuration;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Zomzog on 24/07/2017.
 */
@ToString
@ConfigurationProperties(prefix = "zeshop.common.swagger", ignoreUnknownFields = false)
public class SwaggerProperties {
    private String basePackage = "";
    private final Api api = new Api();
    private final Contact contact = new Contact();


    public String getBasePackage() {
        return this.basePackage;
    }

    public Api getApi() {
        return this.api;
    }

    public void setBasePackage(final String basePackage) {
        this.basePackage = basePackage;
    }


    public Contact getContact() {
        return this.contact;
    }

    @ToString
    public class Api {
        private String name = "";
        private String description = "";
        private String version;

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(final String version) {
            this.version = version;
        }
    }

    @ToString
    public class Contact {
        private String name = "";
        private String url = "";
        private String email = "";

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(final String url) {
            this.url = url;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }
    }
}
