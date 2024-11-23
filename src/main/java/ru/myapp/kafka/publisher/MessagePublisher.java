package ru.myapp.kafka.publisher;

public interface MessagePublisher<T> {

    void publish(T message);
}
