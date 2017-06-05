package bzh.zomzog.zeshop.auth.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Zomzog on 04/06/2017.
 */
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
@Data
public class AuthProperties {
    private Mail mail;

    @Data
    public static class Mail {
        private String from;
        private String baseUrl;
    }
}
