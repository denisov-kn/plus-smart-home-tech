package ru.practicum.service;

import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;
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

    public void sendDeviceActions(List<DeviceActionRequestProto> actions) {
        if (actions.isEmpty()) return;

        for (DeviceActionRequestProto action : actions) {
            stub.handleDeviceAction(action);
        }
    }
}
