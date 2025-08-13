package ru.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.practicum.api.PaymentApi;

@FeignClient(name = "payment")
public interface PaymentClient extends PaymentApi {

}
