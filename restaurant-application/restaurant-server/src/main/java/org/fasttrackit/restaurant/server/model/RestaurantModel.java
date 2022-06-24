package org.fasttrackit.restaurant.server.model;

import java.time.LocalDate;

public record RestaurantModel(Long id,
                              String name,
                              int stars,
                              String city,
                              LocalDate since) {
}
