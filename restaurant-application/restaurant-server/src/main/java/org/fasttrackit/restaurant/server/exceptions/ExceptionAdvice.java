package org.fasttrackit.restaurant.server.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ApiError handleResourceNotFound(RestaurantNotFoundException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }

    @ExceptionHandler(InvalidRestaurantException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleBadRequest(InvalidRestaurantException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }

    @ExceptionHandler(NameAlreadyExistException.class)
    @ResponseStatus(CONFLICT)
    ApiError handleConflict(NameAlreadyExistException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }

    @ExceptionHandler(TimeFormatException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleBadRequest(TimeFormatException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }
}

record ApiError(String code, String message) {
}
