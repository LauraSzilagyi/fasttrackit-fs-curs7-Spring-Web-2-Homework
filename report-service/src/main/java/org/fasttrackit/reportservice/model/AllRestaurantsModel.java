package org.fasttrackit.reportservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AllRestaurantsModel(@JsonProperty("content") List<RestaurantModel> restaurants,
                                  @JsonProperty("last") boolean lastPage) {
}
