package silgar.fmsuploadfile.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import silgar.fmsuploadfile.minio.MinioConfiguration;
import silgar.fmsuploadfile.minio.MinioConfigurationProperties;

import java.io.InputStream;
import java.nio.file.Path;
import io.minio.*;

@Component
public class MinioAdapter {

    @Autowired
    private MinioConfiguration minioConfiguration;

    @Autowired
    private MinioConfigurationProperties minioConfigurationProperties;

    /**
     * Upload a file to Minio
     *
     * @param source      Path with prefix to the object. Object name must be included.
     * @param file        File as an inputstream
     * @param contentType MIME type for the object
     * @throws Exception if an error occur while uploading object
     */
    public void upload(Path source, InputStream file, String contentType) throws Exception {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .contentType(contentType)
                    .build();

            minioConfiguration.minioClient().putObject(args);
        } catch (Exception e) {
            throw new Exception("Error while fetching files in Minio", e);
        }
    }
}
