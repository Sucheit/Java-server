package ru.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class GenerateTestData {


    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedWriter bf = new BufferedWriter(new FileWriter("src/test/resources/test_data/test_data.txt"))) {
            bf.flush();
            for (int i = 0; i < Constants.BATCH_SIZE; i++) {
                String message = objectMapper.writeValueAsString(new BatchMessage(UUID.randomUUID().toString()));
                bf.write(message);
                bf.newLine();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
