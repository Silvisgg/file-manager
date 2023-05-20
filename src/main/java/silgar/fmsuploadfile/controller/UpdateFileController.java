package silgar.fmsuploadfile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import silgar.fmsuploadfile.exception.ErrorMessage;
import silgar.fmsuploadfile.service.StoreService;
import silgar.fmsuploadfile.service.ValidateFileService;

import java.util.Date;

@Slf4j
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
    @ResponseBody
    public Mono<ResponseEntity> handleFileUpload(@RequestParam("file") MultipartFile inbound)  {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "UpdateFileController");

        return Mono.just(inbound)
                .filter(validateFileService::validateFile)
                .flatMap(storeService::store)
                .map( f ->{
                    log.info("grabado sin errores");

                    ResponseEntity responseEntity = ResponseEntity.ok()
                            .headers(headers)
                            .body("You successfully uploaded!!");
                    return responseEntity;

                    //return ResponseEntity.status(f).headers(headers).body("You successfully uploaded!");
                })
                .onErrorResume(throwable -> {
                    ResponseEntity responseEntity = ResponseEntity.badRequest()
                            .headers(headers)
                            .body("You wrong uploaded!!");
                    return Mono.just(responseEntity);
                });
    }

    /*
    Check if controller is well configured and able to send a status and a response
     */
    @GetMapping("/request-args/{text}")
    @ResponseBody
    public ResponseEntity<String> checkWellBehaviour(@PathVariable String text) {
        return ResponseEntity.ok().body(text);
    }

    /*
    Check if the application is running
     */
    @GetMapping(value = "/health")
    @ResponseBody
    public ResponseEntity<String> status() {
        return ResponseEntity.ok().body("Upload File Application is running...");
    }


    private Mono<ResponseEntity> throwExc (Throwable exc){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Controller advice");

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exc.getMessage(),
                "");

        return Mono.just(new ResponseEntity (errorMessage,headers,HttpStatus.INTERNAL_SERVER_ERROR));

    }

}