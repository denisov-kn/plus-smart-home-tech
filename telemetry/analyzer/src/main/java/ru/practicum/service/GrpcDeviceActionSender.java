package ru.practicum.service;

import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;
import ru.practicum.ProtoMappingUtils;
import ru.practicum.model.hubroute.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequestProto;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc.HubRouterControllerBlockingStub;

import java.util.List;

@Service
public class GrpcDeviceActionSender {

    private final HubRouterControllerBlockingStub stub;

    public GrpcDeviceActionSender(@GrpcClient("hub-router")
                                  HubRouterControllerBlockingStub hubRouterClient) {
        stub = hubRouterClient;
    }

    public void sendDeviceActions(List<DeviceActionRequest> actions) {
        if (actions.isEmpty()) return;

        for (DeviceActionRequest action : actions) {
            DeviceActionRequestProto proto = ProtoMappingUtils.mapDeviceActionRequest(action);
            stub.handleDeviceAction(proto);
        }
    }
}
