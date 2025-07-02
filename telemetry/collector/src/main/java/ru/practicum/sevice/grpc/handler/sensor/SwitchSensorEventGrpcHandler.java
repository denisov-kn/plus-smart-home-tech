package ru.practicum.sevice.grpc.handler.sensor;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.SwitchSensorEvent;
import ru.practicum.sevice.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SwitchSensorEventGrpcHandler implements SensorEventGrpcHandler {

    private final KafkaService kafkaService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
       return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {

        SwitchSensorEvent switchSensorEvent = new SwitchSensorEvent();
        switchSensorEvent.setId(event.getId());
        switchSensorEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        switchSensorEvent.setState(event.getSwitchSensorEvent().getState());

        kafkaService.kafkaSensorEvent(switchSensorEvent);
    }
}
