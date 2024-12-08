package pl.io.emergency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HelpRequestNotFound extends RuntimeException {
    public HelpRequestNotFound(String msg) {
        super(msg);
    }
}
