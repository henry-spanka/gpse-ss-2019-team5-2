package gpse.team52.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * StorageProperties.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files.
     */
    private String location = "../gpse-ss-2019-team5-2/src/main/resources/static/pictures";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
