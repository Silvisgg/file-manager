package silgar.fmsuploadfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import silgar.fmsuploadfile.exception.BadRequestException;

import java.util.function.Predicate;

@Service
public class ValidateFileService {

    @Autowired
    private ValidationConfiguration validationConfiguration;

    public Boolean validateFile (MultipartFile file) {

        if (file.isEmpty()){
            throw new BadRequestException("File is empty");
        } else if (file.getSize() > DataSize.parse(validationConfiguration.getMaxFileSize(), DataUnit.MEGABYTES).toBytes() ) {
            throw new BadRequestException("File size is bigger than maximum");
        } else {
            return true;
        }

    }
}
