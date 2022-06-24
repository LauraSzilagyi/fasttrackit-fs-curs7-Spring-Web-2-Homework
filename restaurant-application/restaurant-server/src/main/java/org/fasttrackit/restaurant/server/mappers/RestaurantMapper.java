package org.fasttrackit.restaurant.server.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.restaurant.server.entity.RestaurantEntity;
import org.fasttrackit.restaurant.server.model.RestaurantModel;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantModel entityToModel(RestaurantEntity dbEntity) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.convertValue(dbEntity, org.fasttrackit.restaurant.server.model.RestaurantModel.class);

    }
}

