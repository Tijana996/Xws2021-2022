package app.handlers;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomRestTemplateError extends RuntimeException {
    private HttpStatus statusCode;
    private String error;
}
