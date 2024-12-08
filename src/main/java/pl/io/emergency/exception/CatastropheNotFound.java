package pl.io.emergency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatastropheNotFound extends RuntimeException {
    public CatastropheNotFound(String msg) {
        super(msg);
    }
}
