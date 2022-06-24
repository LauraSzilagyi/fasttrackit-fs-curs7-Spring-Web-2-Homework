package org.fasttrackit.restaurant.server.exceptions;

public class InvalidRestaurantException extends RuntimeException {
    public InvalidRestaurantException(String message) {
        super(message);
    }
}
