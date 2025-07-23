package ru.practicum.service.handler;

import org.apache.avro.specific.SpecificRecordBase;

import java.time.Instant;

public interface HubEventHandler <T extends SpecificRecordBase> {
    void handle(T payload, String hubId, Instant timestamp);
    Class<T> payloadType();
}
