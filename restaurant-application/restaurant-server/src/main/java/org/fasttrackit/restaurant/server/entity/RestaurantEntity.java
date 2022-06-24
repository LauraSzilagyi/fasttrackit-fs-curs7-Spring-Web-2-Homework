package org.fasttrackit.restaurant.server.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stars")
    private int stars;

    @Column(name = "city")
    private String city;

    @Column(name = "since")
    private LocalDate since;

    public RestaurantEntity(String name, int stars, String city, LocalDate since) {
        this(0, name, stars, city, since);
    }
}
