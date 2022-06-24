package org.fasttrackit.restaurant.server.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(long id) {
        super("Restaurant with id %s doesn't exist".formatted(id));
    }
}
