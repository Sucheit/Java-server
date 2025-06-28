package ru.myapp.error;

import static ru.myapp.utils.Constants.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ErrorHandler {

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiError handleThrowable(final Throwable e) {
    return new ApiError("Internal Server error.",
        e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        LocalDateTime.now().format(DATE_TIME_FORMATTER));
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError handleNotFoundException(final NotFoundException e) {
    return new ApiError("The required resource was not found.",
        e.getMessage(),
        HttpStatus.NOT_FOUND.toString(),
        LocalDateTime.now().format(DATE_TIME_FORMATTER));
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleBadRequestException(final BadRequestException e) {
    return new ApiError("The request is incorrect.",
        e.getMessage(),
        HttpStatus.BAD_REQUEST.toString(),
        LocalDateTime.now().format(DATE_TIME_FORMATTER));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
    return new ApiError("The request is incorrect.",
        String.format("Field: %s. Error: %s. Value: %s",
            Objects.requireNonNull(e.getFieldError()).getField(),
            Objects.requireNonNull(e.getDetailMessageArguments())[
                e.getDetailMessageArguments().length - 1],
            e.getFieldError().getRejectedValue()),
        HttpStatus.BAD_REQUEST.toString(),
        LocalDateTime.now().format(DATE_TIME_FORMATTER));
  }

  @ExceptionHandler(FeignRequestException.class)
  public ResponseEntity<ApiError> handleFeignRequestException(final FeignRequestException e) {
    return ResponseEntity
        .status(e.getStatus())
        .body(new ApiError(
            "Feign request error",
            e.getStatus().toString(),
            e.getMessage(),
            LocalDateTime.now().format(DATE_TIME_FORMATTER)));
  }
}
