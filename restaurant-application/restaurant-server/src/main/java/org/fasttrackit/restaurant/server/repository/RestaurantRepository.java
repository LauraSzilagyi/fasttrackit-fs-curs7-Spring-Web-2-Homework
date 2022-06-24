package org.fasttrackit.restaurant.server.repository;

import org.fasttrackit.restaurant.server.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Query(value = """
            SELECT r FROM RestaurantEntity r
            WHERE (r.stars IN (:stars) OR :stars IS NULL)
            AND (r.city = :city OR :city IS NULL)
            """
    )
    Page<RestaurantEntity> findAllCustom(List<Integer> stars, String city, Pageable pageable);
}
