package ru.myapp.kafka.consumer;

public interface MessageListener<T> {

    void listenMessage(T message);
}
