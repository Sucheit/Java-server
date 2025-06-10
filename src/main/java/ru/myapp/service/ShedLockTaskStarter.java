package ru.myapp.service;

import java.time.OffsetDateTime;

public interface ShedLockTaskStarter {

    OffsetDateTime startScheduledTaskWithReturn();
}
