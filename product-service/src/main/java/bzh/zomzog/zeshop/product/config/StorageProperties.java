package bzh.zomzog.zeshop.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Zomzog on 30/06/2017.
 */

@ConfigurationProperties(prefix = "storage", ignoreUnknownFields = false)
public class StorageProperties {
    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return this.location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }
}
