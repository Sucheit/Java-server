package ru.myapp.kafka.publisher;

import java.util.Map;

public interface MessagePublisher {

    void publish(String topic, Integer partition, String key, Object message, Map<String, String> headers);

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
}
