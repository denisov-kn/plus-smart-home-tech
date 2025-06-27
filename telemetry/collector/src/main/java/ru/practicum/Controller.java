package ru.practicum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.sevice.CollectorService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class Controller {
    private final CollectorService collectorService;

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        log.info("Input sensor event: {}", sensorEvent);
        collectorService.collectSensorEvent(sensorEvent);
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void addHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        log.info("Input hub event: {}", hubEvent);
        collectorService.collectHubEvent(hubEvent);
    }


}
