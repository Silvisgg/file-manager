package silgar.fmsuploadfile.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class ErrorMessage {

        private int statusCode;
        private Date timestamp;
        private String message;
        private String path;

    }
