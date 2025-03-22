package ru.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.mappers.MessageMapper;
import ru.myapp.persistence.repository.MessageRepository;
import ru.myapp.service.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public void saveMessage(BatchMessage batchMessage) {
        messageRepository.save(messageMapper.toEntity(batchMessage));
    }
}
