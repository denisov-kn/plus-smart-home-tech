package ru.practicum.sevice;

import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.sensor.SensorEvent;

public interface CollectorService {
    void collectSensorEvent(SensorEvent sensorEvent);
    void collectHubEvent(HubEvent hubEvent);

}
