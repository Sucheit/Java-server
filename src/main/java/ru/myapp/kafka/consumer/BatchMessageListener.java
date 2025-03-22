package ru.myapp.kafka.consumer;

import java.util.List;

public interface BatchMessageListener<T> {

    void listenBatchMessages(List<T> messages) throws InterruptedException;
}
