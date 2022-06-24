package org.fasttrackit.restaurant.server;

import org.fasttrackit.restaurant.server.entity.RestaurantEntity;
import org.fasttrackit.restaurant.server.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class RestaurantServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServerApplication.class, args);
    }

    @Bean
    CommandLineRunner atStartup(RestaurantRepository repository) {
        return args -> repository.saveAll(List.of(
                new RestaurantEntity("Boema", 3, "Marghita", LocalDate.of(2001, 5, 30)),
                new RestaurantEntity("Margareta", 4, "Timisoara", LocalDate.of(1998, 4, 1)),
                new RestaurantEntity("Perla", 5, "Oradea", LocalDate.of(2015, 1, 13)),
                new RestaurantEntity("Alexan", 2, "Suplac", LocalDate.of(2005, 9, 5)),
                new RestaurantEntity("Sergiana", 5, "Brasov", LocalDate.of(2020, 10, 15))
        ));
    }
}
