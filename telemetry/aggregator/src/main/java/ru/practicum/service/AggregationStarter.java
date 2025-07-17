package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final KafkaConsumer<String, SpecificRecordBase> consumer;

    private final SnapshotStateHolder snapshotStateHolder = new SnapshotStateHolder();

    @Value("${kafka.topics.sensor-events}")
    private String sensorTopic;

    @Value("${kafka.topics.sensor-snapshot}")
    private String snapshotTopic;

    /**
     * Метод для начала процесса агрегации данных.
     * Подписывается на топики для получения событий от датчиков,
     * формирует снимок их состояния и записывает в кафку.
     */
    public void start() {
        try {

            log.info("Aggregator subscribed to topic: {}", sensorTopic);
            consumer.subscribe(List.of(sensorTopic));

            while (true) {

                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {

                    if(!(record.value() instanceof SensorEventAvro event)) {
                        log.warn("Unexpected record type: {}", record.value().getClass().getSimpleName());
                        continue;
                    }

                    Optional<SensorsSnapshotAvro> updated = snapshotStateHolder.updateState(event);
                    updated.ifPresent(snapshot -> {
                        try {
                            producer.send(new ProducerRecord<>(snapshotTopic, snapshot));
                            log.info("Updated snapshot for hub {} sent {}", snapshot.getHubId(), snapshot);
                        } catch (Exception e) {
                            log.error("Failed to send snapshot to Kafka", e);
                        }
                    });
                }
                consumer.commitAsync();
            }

        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {

            try {

                producer.flush();
                consumer.commitSync();

            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }
}
