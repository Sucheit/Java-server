package ru.myapp.config;

import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import ru.myapp.config.kafka.MyCustomRetryer;

@Configuration
public class FeignClientConfig {

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // таймаут подключения
        .readTimeout(60, TimeUnit.SECONDS)     // таймаут чтения
        .writeTimeout(60, TimeUnit.SECONDS)    // таймаут записи
        .retryOnConnectionFailure(true)        // повтор при ошибке соединения
        .build();
  }

  @Bean
  public Retryer feignRetryer() {
    return new MyCustomRetryer(200, 5);
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return (methodKey, response) -> {
      if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
        return new RetryableException(
            response.status(),
            response.reason(),
            response.request().httpMethod(),
            5L,
            response.request()
        );
      }
      // Другая обработка ошибок
      return new RuntimeException("Custom error handling");
    };
  }
}
