package ru.practicum.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Ошибка валидации поля %s: %s. Некорректное значение: %s",
                        error.getField(),
                        error.getDefaultMessage(),
                        Objects.toString(error.getRejectedValue(), "null")))
                .collect(Collectors.toList());

        List<String> globalErrors = e.getBindingResult().getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        fieldErrors.addAll(globalErrors);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrectly made request.", fieldErrors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintViolationException(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream()
                .map(violation -> String.format("Ошибка: %s. Значение: %s",
                        violation.getMessage(),
                        violation.getInvalidValue()))
                .collect(Collectors.toList());

        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrectly made request.", errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onNotFoundException(NotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "The required object was not found.", e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onNotAuthorizedException(NotAuthorizedUserException e) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, "The user is not authorized", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onNoProductsInShoppingCartException(NoProductsInShoppingCartException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "No product in shopping cart", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onProductInShoppingCartLowQuantityInWarehouseException(ProductInShoppingCartLowQuantityInWarehouse e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Low quantity of product in shopping cart ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onOrderFoundException(NoOrderFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "The order was not found.", e.getMessage());
    }


}
