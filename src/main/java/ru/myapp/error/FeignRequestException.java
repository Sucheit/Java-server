package ru.myapp.error;

import org.springframework.http.HttpStatus;

public class FeignRequestException extends RuntimeException {

    private final HttpStatus status;

    public FeignRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
