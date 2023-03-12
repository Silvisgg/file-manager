package silgar.fmsuploadfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import silgar.fmsuploadfile.adapter.MinioAdapter;

import java.nio.file.Path;

@Service
public class UpdateMinioService implements IUpdateFileService{

    private final MinioAdapter minioAdapter;

    @Autowired
    public UpdateMinioService(MinioAdapter minioAdapter) {
        this.minioAdapter = minioAdapter;
    }


    public void store (MultipartFile file) throws Exception {
        Path path = Path.of(file.getOriginalFilename());

        minioAdapter.upload(path, file.getInputStream(), file.getContentType());
    }
}
