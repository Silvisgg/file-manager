package silgar.fmsuploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import silgar.fmsuploadfile.service.StoreService;
import silgar.fmsuploadfile.service.ValidateFileService;

@RestController
public class UpdateFileController {

    private final StoreService storeService;
    private final ValidateFileService validateFileService;

    @Autowired
    public UpdateFileController (StoreService storeService, ValidateFileService validateFileService){
        this.storeService = storeService;
        this.validateFileService = validateFileService;
    }

    /*
    Upload a file to the Storage if file passes validations
     */
    @PostMapping(path = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        validateFileService.validate(file);
        storeService.store(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo - Department");

        return ResponseEntity.ok().headers(headers).body("You successfully uploaded " + file.getOriginalFilename() + "!");
    }

    /*
    Check if controller is well configured and able to send a status and a response
     */
    @GetMapping("/request-args/{text}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> checkWellBehaviour(@PathVariable String text) {
        return ResponseEntity.ok().body(text);
    }

    /*
    Check if the application is running
     */
    @GetMapping(value = "/health")
    @ResponseBody
    public String status() {
        return "Upload File Application is running...";
    }


}