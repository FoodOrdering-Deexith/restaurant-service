package com.fullstack.restaurant_service.service;

import com.fullstack.restaurant_service.dto.RestaurantDTO;
import com.fullstack.restaurant_service.entity.Restaurant;
import com.fullstack.restaurant_service.mapper.RestaurantMapper;
import com.fullstack.restaurant_service.repository.RestaurantRepo;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    public List<RestaurantDTO> fetchAll() {
        List<Restaurant> restaurants = this.restaurantRepo.findAll();
        List<RestaurantDTO> restaurantDTOS = restaurants.stream().map(RestaurantMapper.INSTANCE::restaurantToRestaurantDTO).collect(Collectors.toList());
        return restaurantDTOS;
    }

    public RestaurantDTO fetchRestaurant(UUID uuid) {
        Optional<Restaurant> restaurant = this.restaurantRepo.findById(uuid);
        if (!restaurant.isEmpty()) return RestaurantMapper.INSTANCE.restaurantToRestaurantDTO(restaurant.get());
        throw new NotFoundException("Restaurant could not be found.");
    }

    public UUID add(RestaurantDTO restaurantDTO) {
        Restaurant res = restaurantRepo.saveAndFlush(RestaurantMapper.INSTANCE.restaurantDTOToRestaurant(restaurantDTO));
        return res.getId();
    }
}
