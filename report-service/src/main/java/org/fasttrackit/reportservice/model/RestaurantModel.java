package org.fasttrackit.reportservice.model;

import java.time.LocalDate;

public record RestaurantModel(Long id, String name, Integer stars, String city, LocalDate since) {
}
