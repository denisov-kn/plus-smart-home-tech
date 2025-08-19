package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.ShoppingCartApi;
import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShoppingCartController implements ShoppingCartApi {

    private final ShoppingCartService shoppingCartService;

    @Override
    public ShoppingCartDto getShoppingCart(String username) {
        log.info("Get shopping cart for {}", username);
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart(username);
        log.info("Get shopping cart {}", shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCartDto updateShoppingCart(String username, Map<UUID, Integer> products) {
        log.info("Update shopping cart for {}", username);
        log.info("Updated products {}", products);
        ShoppingCartDto shoppingCart = shoppingCartService.updateShoppingCart(username, products);
        log.info("Update shopping cart {}", shoppingCart);
        return shoppingCart;
    }

    @Override
    public void deleteShoppingCart(String username) {
        log.info("Delete shopping cart for {}", username);
        shoppingCartService.deleteShoppingCart(username);
        log.info("Shopping cart was deleted");
    }

    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String username, List<String> products) {
        log.info("Remove products from cart for {}", username);
        log.info("Removed products {}", products);
        ShoppingCartDto shoppingCart = shoppingCartService.removeProductsFromShoppingCart(username, products);
        log.info("Shopping cart with removed products {}", shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCartDto changeQuantityShoppingCart(String username, ChangeProductQuantityRequest request) {
        log.info("Change quantity shopping cart for {}", username);
        log.info("Changed quantity shopping cart request {}", request);
        ShoppingCartDto shoppingCart = shoppingCartService.changeQuantityShoppingCart(username, request);
        log.info("Changed quantity shopping cart {}", shoppingCart);
        return shoppingCart;
    }
}
