package ru.practicum.service.grpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.service.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrpcEventServiceImpl implements GrpcEventService {

    private final KafkaService kafkaService;


    @Override
    public void processSensorEvent(SensorEventProto sensorEventProto) {

        log.info("Processing Proto Sensor Event: {}", sensorEventProto);
        kafkaService.kafkaSensorEvent(sensorEventProto);

    }

    @Override
    public void processHubEvent(HubEventProto hubEventProto) {

        log.info("Processing Proto Hub Event: {}", hubEventProto);
        kafkaService.kafkaHubEvent(hubEventProto);

    }
}
