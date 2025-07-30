package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ShoppingCartRepository;
import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.exception.NotAuthorizedUserException;
import ru.practicum.model.Cart;
import ru.practicum.model.CartProduct;
import ru.practicum.model.enums.CartState;
import ru.practicum.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartDto getShoppingCart(String username) {

        return Mapper.toShoppingCartDto(findShoppingCart(username));
    }

    @Override
    public ShoppingCartDto updateShoppingCart(String username, Map<String, Integer> products) {

        Cart cart = shoppingCartRepository.findByUsernameAndState(username, CartState.ACTIVE)
                .orElseGet( () -> {
                    Cart newCart = Cart.builder()
                            .username(username)
                            .products(new ArrayList<>())
                            .state(CartState.ACTIVE)
                            .build();
                    return shoppingCartRepository.save(newCart);
                });
        List<CartProduct> cartProduct = cart.getProducts();
        List<CartProduct> cartProductsToUpdate = Mapper.toCartProduct(cart, products);
        cartProduct.addAll(cartProductsToUpdate);

        shoppingCartRepository.save(cart);

        return Mapper.toShoppingCartDto(cart);

    }

    @Override
    public void deleteShoppingCart(String username) {
        Cart cart = findShoppingCart(username);
        cart.setState(CartState.DEACTIVATE);
        shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String username, List<String> products) {
        Cart cart = findShoppingCart(username);
        List<CartProduct> cartProduct = cart.getProducts();
        cartProduct.removeIf(cp -> products.contains(cp.getProductId().toString()));

        cart.setProducts(cartProduct);

        shoppingCartRepository.save(cart);

        return Mapper.toShoppingCartDto(cart);
    }

    @Override
    public ShoppingCartDto changeQuantityShoppingCart(String username, ChangeProductQuantityRequest request) {
        Cart cart = findShoppingCart(username);
        List<CartProduct> cartProducts = cart.getProducts();

        for (CartProduct cp : cartProducts) {
            if (cp.getProductId().equals(UUID.fromString(request.getProductId()))) {
                cp.setQuantity(request.getNewQuantity());
                break;
            }
        }

        shoppingCartRepository.save(cart);

        return Mapper.toShoppingCartDto(cart);

    }

    private Cart findShoppingCart(String username) {
        return shoppingCartRepository.findByUsernameAndState(username, CartState.ACTIVE)
                .orElseThrow(() -> new NotAuthorizedUserException("Корзина пользователя с таким username: " + username + " не найдена"));
    }
}
