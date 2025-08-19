package ru.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.api.DeliveryApi;

@FeignClient(name = "delivery")
public interface DeliveryClient extends DeliveryApi {
}
