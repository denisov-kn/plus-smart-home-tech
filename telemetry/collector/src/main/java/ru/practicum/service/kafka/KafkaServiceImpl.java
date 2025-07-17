package ru.practicum.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.service.kafka.handler.hub.HubEventHandlerRegistry;
import ru.practicum.service.kafka.handler.sensor.SensorEventRegistry;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final HubEventHandlerRegistry hubRegistry;
    private final SensorEventRegistry sensorRegistry;

    @Value("${kafka.topics.sensor-events}")
    private String sensorTopic;

    @Value("${kafka.topics.hub-events}")
    private String hubTopic;

    @Override
    public void kafkaSensorEvent(SensorEventProto sensorEvent) {
        log.info("Sensor proto event received: {}", sensorEvent);
        SensorEventAvro avro = sensorRegistry.handle(sensorEvent);
        log.info("Avro sensor event: {}", avro);
        producer.send(new ProducerRecord<>(sensorTopic, avro));
    }

    @Override
    public void kafkaHubEvent(HubEventProto hubEvent) {
        log.info("Hub event proto received: {}", hubEvent);
        HubEventAvro avro = hubRegistry.handle(hubEvent);
        log.info("Avro hub event: {}", avro);
        producer.send(new ProducerRecord<>(hubTopic, avro));
    }
}
