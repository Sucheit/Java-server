package ru.myapp.config.kafka;

import lombok.Data;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProps {

  @NestedConfigurationProperty
  private BackOff backOff;
  @NestedConfigurationProperty
  private Topics topics;
  @NestedConfigurationProperty
  private Listener listener;
  @NestedConfigurationProperty
  private KafkaProperties connection;

  @Data
  public static class Topics {

    private String users;
    private String items;
    private String dlt;
    private String batchMessages;
  }

  @Data
  public static class BackOff {

    private Long interval;
    private Long attempts;
  }

  @Data
  public static class Listener {

    private Integer concurrency;
  }
}
