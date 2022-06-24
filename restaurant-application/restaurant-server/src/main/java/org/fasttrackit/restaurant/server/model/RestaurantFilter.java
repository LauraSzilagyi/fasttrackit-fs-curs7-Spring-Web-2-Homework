package org.fasttrackit.restaurant.server.model;

import java.util.List;

public record RestaurantFilter(List<Integer> stars, String city) {
}
