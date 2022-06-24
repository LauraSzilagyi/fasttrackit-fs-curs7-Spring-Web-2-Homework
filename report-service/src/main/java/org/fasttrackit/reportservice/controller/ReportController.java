package org.fasttrackit.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.reportservice.exceptions.RestaurantNotFoundException;
import org.fasttrackit.reportservice.model.RestaurantModel;
import org.fasttrackit.reportservice.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("restaurants/{id}")
    RestaurantModel getOneRestaurant(@PathVariable Long id) {
        return service.getOne(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @GetMapping("restaurants")
    List<RestaurantModel> getAll() {
        return service.getAll();
    }
}
