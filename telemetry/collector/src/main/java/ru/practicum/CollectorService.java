package ru.practicum;

import ru.practicum.dto.hub.event.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;

public interface CollectorService {
    void collectSensorEvent(SensorEvent sensorEvent);
    void collectHubEvent(HubEvent hubEvent);

}
