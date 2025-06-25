package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.dto.hub.event.HubEvent;
import ru.practicum.dto.sensor.SensorEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


@Service
@RequiredArgsConstructor
public class CollectorServiceImpl implements CollectorService {

    private final KafkaProducer<String, SpecificRecordBase> producer;

    @Value("${kafka.topics.sensor-events}")
    private String sensorTopic;

    @Value("${kafka.topics.hub-events}")
    private String hubTopic;

    @Override
    public void collectSensorEvent(SensorEvent sensorEvent) {
        SensorEventAvro avro = SensorEventMapper.toAvro(sensorEvent);
        producer.send(new ProducerRecord<>(sensorTopic, avro));
    }

    @Override
    public void collectHubEvent(HubEvent hubEvent) {

        HubEventAvro avro = HubEventMapper.toAvro(hubEvent);
        producer.send(new ProducerRecord<>(hubTopic, avro));

    }
}
