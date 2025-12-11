package ru.myapp.kafka.publisher;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

public interface MessagePublisher {

  void publish(String topic, Integer partition, String key, Object message,
      Map<String, String> headers);

  default void publish(String topic, String key, Object message, Map<String, String> headers) {
    this.publish(topic, null, key, message, headers);
  }

  default void publish(String topic, String key, Object message) {
    this.publish(topic, null, key, message, Map.of());
  }

  default void publish(String topic, Object message, Map<String, String> headers) {
    this.publish(topic, null, null, message, headers);
  }

  default void publish(String topic, Object message) {
    this.publish(topic, null, null, message, Map.of());
  }

  default List<Header> mapToHeaders(Map<String, String> headers) {
    return headers.entrySet().stream()
        .map(e -> (Header) new RecordHeader(e.getKey(),
            e.getValue().getBytes(StandardCharsets.UTF_8)))
        .toList();
  }
}
