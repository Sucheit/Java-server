package ru.myapp.utils;

import com.google.protobuf.Timestamp;

import java.time.Instant;

public interface GoogleTimestampConverter {

    static Timestamp instantToTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    static Instant timestampToInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos()
        );
    }
}
