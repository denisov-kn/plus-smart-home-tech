package ru.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.api.WarehouseApi;

@FeignClient(name = "warehouse")
public interface WarehouseClient extends WarehouseApi {
}
