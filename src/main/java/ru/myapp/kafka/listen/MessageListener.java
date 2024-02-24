package ru.myapp.kafka.listen;

public interface MessageListener<T> {

    void listenMessage(T message);
}
