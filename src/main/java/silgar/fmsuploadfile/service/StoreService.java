package silgar.fmsuploadfile.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.service.store")
public class StoreService {

    private String uri;

    public Mono<HttpStatus> store(MultipartFile file) throws Exception{
        log.info("Starting NON-BLOCKING Controller!");
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());

        Mono<HttpStatus> resultMono = WebClient.create()
                .post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        log.info("OK: storing file in uri: "+ uri);
                        return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
                    } else {
                        log.error("Error: storing file in uri: "+ uri);
                        return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
                    }
                });

        resultMono.subscribe();
        log.info("Exiting NON-BLOCKING Controller!");
        return resultMono;
    }

}
