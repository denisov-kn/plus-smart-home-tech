package ru.practicum.sevice.grpc;

import org.springframework.stereotype.Service;
import ru.practicum.sevice.grpc.handler.hub.HubEventGrpcHandler;
import ru.practicum.sevice.grpc.handler.sensor.SensorEventGrpcHandler;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class GrpcEventServiceImpl implements GrpcEventService {

    private final Map<HubEventProto.PayloadCase, HubEventGrpcHandler> hubEventHandlers;
    private final Map<SensorEventProto.PayloadCase, SensorEventGrpcHandler> sensorEventHandlers;


    public GrpcEventServiceImpl(List<HubEventGrpcHandler> hubEventHandlers, List<SensorEventGrpcHandler> sensorEventGrpcHandlers) {
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(
                        HubEventGrpcHandler::getMessageType,
                        Function.identity()
                ));
        this.sensorEventHandlers = sensorEventGrpcHandlers.stream()
                .collect(Collectors.toMap(
                        SensorEventGrpcHandler::getMessageType,
                        Function.identity()
                ));
    }

    @Override
    public void processSensorEvent(SensorEventProto sensorEventProto) {

        if (sensorEventHandlers.containsKey(sensorEventProto.getPayloadCase()))
            sensorEventHandlers.get(sensorEventProto.getPayloadCase()).handle(sensorEventProto);
        else
            throw new IllegalArgumentException("Не могу найти обработчик для события " + sensorEventProto.getPayloadCase());
    }

    @Override
    public void processHubEvent(HubEventProto hubEventProto) {
        if (hubEventHandlers.containsKey(hubEventProto.getPayloadCase()))
            hubEventHandlers.get(hubEventProto.getPayloadCase()).handle(hubEventProto);
        else
            throw new IllegalArgumentException("Не могу найти обработчик для события " + hubEventProto.getPayloadCase());
    }
}
