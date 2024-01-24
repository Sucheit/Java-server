package ru.myapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Objects;

import static ru.myapp.utils.Constants.DATE_TIME_FORMATTER;


@ControllerAdvice
@ResponseBody
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e) {
        return new ApiError("Internal Server error.",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return new ApiError("The required resource was not found.",
                e.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        return new ApiError("The request is incorrect.",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return new ApiError("The request is incorrect.",
                String.format("Field: %s. Error: %s. Value: %s",
                        Objects.requireNonNull(e.getFieldError()).getField(),
                        Objects.requireNonNull(e.getDetailMessageArguments())[e.getDetailMessageArguments().length - 1],
                        e.getFieldError().getRejectedValue()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }
}
