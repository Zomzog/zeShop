package bzh.zomzog.zeshop.auth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Zomzog on 04/06/2017.
 */
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
public class AuthProperties {
    private Mail mail;

    public Mail getMail() {
        return this.mail;
    }

    public void setMail(final Mail mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "AuthProperties{" +
                "mail=" + this.mail +
                '}';
    }

    /**
     * Properties for mails management
     */
    public static class Mail {
        private String from;
        private String baseUrl;

        public String getFrom() {
            return this.from;
        }

        public void setFrom(final String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return this.baseUrl;
        }

        public void setBaseUrl(final String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public String toString() {
            return "Mail{" +
                    "from='" + this.from + '\'' +
                    ", baseUrl='" + this.baseUrl + '\'' +
                    '}';
        }
    }
}
