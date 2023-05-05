package silgar.fmsuploadfile.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataUnit;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.servlet.multipart")
public class ValidationConfiguration {

    @DataSizeUnit(DataUnit.MEGABYTES)
    private String maxFileSize;

    @DataSizeUnit(DataUnit.MEGABYTES)
    private String maxRequestSize;

    private String enabled;


}
