package silgar.fmsuploadfile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import silgar.fmsuploadfile.exception.BadRequestException;

@Service
public class ValidateFileService {

    @Value("${max-file-size}")
    private long maxSizeFile;

    public boolean validateFile (MultipartFile file) {
        if (file.isEmpty()){
            throw new BadRequestException("File is empty");
        } else if (file.getSize() > maxSizeFile) {
            throw new BadRequestException("File size is bigger than maximum");
        } else {
            return true;
        }
    }
}
