package ru.practicum;

import org.springframework.stereotype.Service;
import ru.practicum.dto.hub.event.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;


@Service
public class CollectorServiceImpl implements CollectorService {
    @Override
    public void collectSensorEvent(SensorEvent sensorEvent) {

    }

    @Override
    public void collectHubEvent(HubEvent hubEvent) {

    }
}
