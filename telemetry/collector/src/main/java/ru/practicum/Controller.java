package ru.practicum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.hub.event.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class Controller {
    private final CollectorService collectorService;

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        collectorService.collectSensorEvent(sensorEvent);
    }

    @PostMapping("/hub")
    @ResponseStatus(HttpStatus.OK)
    public void addHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        collectorService.collectHubEvent(hubEvent);
    }


}
