package ru.myapp.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    log.error("Status code " + response.status() + ", methodKey = " + methodKey);
    return new FeignRequestException(HttpStatus.valueOf(response.status()),
        HttpStatus.valueOf(response.status()).getReasonPhrase());
  }
}
