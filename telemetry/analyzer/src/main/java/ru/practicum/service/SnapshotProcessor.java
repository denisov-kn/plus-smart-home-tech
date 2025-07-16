package ru.practicum.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequestProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;


@Component
@Slf4j
public class SnapshotProcessor {


    @Autowired
    @Qualifier("kafkaConsumerSnapshot")
    private KafkaConsumer<String, SpecificRecordBase> consumer;


    @Autowired
    private GrpcDeviceActionSender grpcSender;

    @Autowired
    ScenarioEvaluatorService evaluator;



    @Value("${kafka.topics.sensor-snapshot}")
    private String topic;

    public void start() {
        try {

            log.info("Analyzer(SnapshotProcessor) subscribed to topic: {}", topic);
            consumer.subscribe(List.of(topic));

            while (true) {

                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {

                    if(!(record.value() instanceof SensorsSnapshotAvro sensorsSnapshotAvro)) {
                        log.warn("Unexpected record type: {}", record.value().getClass().getSimpleName());
                        continue;
                    }

                    List<DeviceActionRequestProto> deviceActionRequests =  evaluator.evaluate(sensorsSnapshotAvro);

                    log.info("Analyzer send deviceActionRequests {} ", deviceActionRequests);

                    grpcSender.sendDeviceActions(deviceActionRequests);


                }
                consumer.commitAsync();
            }

        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий снепшотов датчиков", e);
        } finally {

            try {

                consumer.commitSync();

            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();

            }
        }
    }

}
