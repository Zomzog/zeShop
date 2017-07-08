package bzh.zomzog.zeshop.auth.configuration;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Zomzog on 04/06/2017.
 */
@ToString
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
public class AuthProperties {

    private final Async async = new Async();
    private final Mail mail = new Mail();

    public Async getAsync() {
        return this.async;
    }

    public Mail getMail() {
        return this.mail;
    }

    /**
     * Properties for async tasks
     */
    @ToString
    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return this.corePoolSize;
        }

        public void setCorePoolSize(final int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return this.maxPoolSize;
        }

        public void setMaxPoolSize(final int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return this.queueCapacity;
        }

        public void setQueueCapacity(final int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    /**
     * Properties for mails management
     */
    @ToString
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
    }
}
