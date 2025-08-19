package ru.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.api.OrderApi;

@FeignClient(name = "order")
public interface OrderClient extends OrderApi {
}
