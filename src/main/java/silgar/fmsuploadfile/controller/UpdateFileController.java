package silgar.fmsuploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import silgar.fmsuploadfile.service.IUpdateFileService;

@RestController
@RequestMapping(produces = {"application/json", "application/xml"})
public class UpdateFileController {

    private final IUpdateFileService iUpdateFileService;

    @Autowired
    public UpdateFileController (IUpdateFileService updateFileService){
        this.iUpdateFileService = updateFileService;
    }

    /*
    Upload a file to the Storage
     */
    @PostMapping(path = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        iUpdateFileService.store(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo - Department");

        return ResponseEntity.ok().headers(headers).body("You successfully uploaded " + file.getOriginalFilename() + "!");
    }

    /*
    Check if controller is well configured and able to send a status and a response
     */
    @GetMapping("/correct/{text}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> checkWellBehaviour(@PathVariable String text) {
        return ResponseEntity.ok().body(text);
    }

    /*
    Check if the application is running
     */
    @GetMapping(value = "/status")
    @ResponseBody
    public String status() {
        return "Upload File Application is running...";
    }


}