package ru.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.api.ShoppingStoreApi;

@FeignClient(name = "shopping-store")
public interface ShoppingStoreClient extends ShoppingStoreApi {
}
