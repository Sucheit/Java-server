package ru.myapp.kafka.publish;

public interface MessagePublisher<T> {

    void publish(T message);
}
