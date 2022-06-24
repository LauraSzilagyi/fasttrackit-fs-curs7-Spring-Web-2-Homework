package org.fasttrackit.restaurant.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.restaurant.server.entity.RestaurantEntity;
import org.fasttrackit.restaurant.server.exceptions.InvalidRestaurantException;
import org.fasttrackit.restaurant.server.exceptions.NameAlreadyExistException;
import org.fasttrackit.restaurant.server.exceptions.RestaurantNotFoundException;
import org.fasttrackit.restaurant.server.exceptions.TimeFormatException;
import org.fasttrackit.restaurant.server.mappers.RestaurantMapper;
import org.fasttrackit.restaurant.server.model.RestaurantFilter;
import org.fasttrackit.restaurant.server.model.RestaurantModel;
import org.fasttrackit.restaurant.server.repository.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    public Page<RestaurantEntity> getAll(RestaurantFilter filter, Pageable pageable) {
        return repository.findAllCustom(filter.stars(), filter.city(), pageable);
    }

    public Optional<RestaurantEntity> getRestaurantById(Long id) {
        return repository.findById(id);
    }

    public RestaurantEntity addNewRestaurant(RestaurantModel model) {
        validateRestaurant(model);
        RestaurantEntity entity = RestaurantEntity.builder()
                .name(model.name())
                .stars(model.stars())
                .city(model.city())
                .since(model.since())
                .build();

        return repository.save(entity);
    }

    private void validateRestaurant(RestaurantModel model) {
        mustContainsNameAndCity(model);
        checkIfNameAlreadyExist(model);
        validDateFormat(model);
    }

    private void validDateFormat(RestaurantModel model) {
        if (model.since().isAfter(LocalDate.now())) {
            throw new TimeFormatException("Time format is not allowed");
        }
    }

    private void mustContainsNameAndCity(RestaurantModel model) {
        if (isEmpty(model.name()) || isEmpty(model.city())) {
            throw new InvalidRestaurantException("A new restaurant must contains name and city!");
        }
    }

    private void checkIfNameAlreadyExist(RestaurantModel model) {
        boolean duplicateName = repository.findAll().stream()
                .anyMatch(restaurant -> restaurant.getName().equalsIgnoreCase(model.name()));
        if (duplicateName) {
            throw new NameAlreadyExistException("Name already exist!");
        }
    }

    public RestaurantEntity replaceRestaurant(Long id, RestaurantModel model) {
        RestaurantEntity dbEntity = repository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        if (isRestaurantNameChanged(model, dbEntity)) {
            checkIfNameAlreadyExist(model);
        }

        dbEntity.setName(model.name());
        dbEntity.setCity(model.city());
        dbEntity.setStars(model.stars());

        return repository.save(dbEntity);
    }

    private boolean isRestaurantNameChanged(RestaurantModel model, RestaurantEntity dbEntity) {
        return !dbEntity.getName().equalsIgnoreCase(model.name());
    }

    public RestaurantEntity updateRestaurant(Long id, JsonPatch jsonPatch) {
        return repository.findById(id)
                .map(dbEntity -> applyPatch(dbEntity, jsonPatch))
                .map(dbEntity -> replaceRestaurant(id, restaurantMapper.entityToModel(dbEntity)))
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    private RestaurantEntity applyPatch(RestaurantEntity dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.findAndRegisterModules();
            JsonNode jsonNode = jsonMapper.convertValue(dbEntity, JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            return jsonMapper.treeToValue(patchedJson, RestaurantEntity.class);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<RestaurantEntity> deleteRestaurant(Long id) {
        Optional<RestaurantEntity> entity = repository.findById(id);
        entity.ifPresent(repository::delete);
        return entity;
    }
}
