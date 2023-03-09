package silgar.fmsuploadfile.service;

import org.springframework.web.multipart.MultipartFile;


public interface IUpdateFileService {

    public void store (MultipartFile file) throws Exception;

}
