package ru.practicum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.sevice.CollectorService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class Controller {
    private final CollectorService collectorService;

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        collectorService.collectSensorEvent(sensorEvent);
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void addHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        collectorService.collectHubEvent(hubEvent);
    }


}
