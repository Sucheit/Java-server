package ru.myapp.kafka.publisher;

import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import ru.myapp.dto.request.MessagePublisherDto;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public interface BatchMessagePublisher {

    void publishBatch(String topic, List<MessagePublisherDto> messageDtoList);


    default List<Header> mapToHeaders(Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(e -> (Header) new RecordHeader(e.getKey(), e.getValue().getBytes(StandardCharsets.UTF_8)))
                .toList();
    }
}
