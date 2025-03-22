package ru.myapp.service;

import ru.myapp.dto.request.BatchMessage;

public interface MessageService {

    void saveMessage(BatchMessage batchMessage);
}
