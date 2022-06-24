package org.fasttrackit.reportservice.service;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.reportservice.model.AllRestaurantsModel;
import org.fasttrackit.reportservice.model.RestaurantModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RestTemplate restTemplate;

    public Optional<RestaurantModel> getOne(Long id) {
        String url = getOneRestaurantUrl(id);

        try {
            RestaurantModel restaurant = restTemplate.getForObject(url, RestaurantModel.class);
            return ofNullable(restaurant);
        } catch (Exception e) {
            return empty();
        }
    }

    public List<RestaurantModel> getAll() {
        List<RestaurantModel> restaurants = new ArrayList<>();
        AtomicInteger page = new AtomicInteger();
        AtomicBoolean lastPage = new AtomicBoolean(false);

        do {
            Optional<AllRestaurantsModel> response = getRestaurants(restTemplate, page.get());

            response.ifPresent(allRestaurantsModel -> {
                restaurants.addAll(allRestaurantsModel.restaurants());
                lastPage.set(allRestaurantsModel.lastPage());
                page.getAndIncrement();
            });
        } while (!lastPage.get());
        return restaurants;
    }

    private Optional<AllRestaurantsModel> getRestaurants(RestTemplate restTemplate, int pageNumber) {
        String url = getAllRestaurantsUrlPaginated(pageNumber);
        try {
            AllRestaurantsModel response = restTemplate.getForObject(url, AllRestaurantsModel.class);
            return ofNullable(response);
        } catch (Exception e) {
            return empty();
        }
    }

    private String getOneRestaurantUrl(Long id) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080")
                .pathSegment("restaurants")
                .pathSegment(String.valueOf(id))
                .build().toUriString();
    }

    private String getAllRestaurantsUrlPaginated(int pageNumber) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080")
                .pathSegment("restaurants")
                .queryParam("page", pageNumber)
                .queryParam("size", 1000)
                .build().toUriString();
    }
}
