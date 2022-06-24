package org.fasttrackit.restaurant.server.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.restaurant.server.entity.RestaurantEntity;
import org.fasttrackit.restaurant.server.exceptions.RestaurantNotFoundException;
import org.fasttrackit.restaurant.server.model.RestaurantFilter;
import org.fasttrackit.restaurant.server.model.RestaurantModel;
import org.fasttrackit.restaurant.server.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurants")
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping
    Page<RestaurantEntity> getAll(RestaurantFilter filter, Pageable pageable) {
        return service.getAll(filter, pageable);
    }

    @GetMapping("{id}")
    RestaurantEntity getRestaurantById(@PathVariable Long id) {
        return service.getRestaurantById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @PostMapping
    RestaurantEntity addNewRestaurant(@RequestBody RestaurantModel model) {
        return service.addNewRestaurant(model);
    }

    @PutMapping("{id}")
    RestaurantEntity replaceRestaurant(@PathVariable Long id, @RequestBody RestaurantModel model) {
        return service.replaceRestaurant(id, model);
    }

    @PatchMapping("{id}")
    RestaurantEntity updateRestaurant(@PathVariable Long id, @RequestBody JsonPatch model) {
        return service.updateRestaurant(id, model);
    }

    @DeleteMapping("{id}")
    RestaurantEntity deleteRestaurant(@PathVariable Long id) {
        return service.deleteRestaurant(id)
                .orElse(null);
    }
}