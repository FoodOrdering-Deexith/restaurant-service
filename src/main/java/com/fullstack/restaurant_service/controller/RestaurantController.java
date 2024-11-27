package com.fullstack.restaurant_service.controller;

import com.fullstack.restaurant_service.dto.RestaurantDTO;
import com.fullstack.restaurant_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants() {
        return new ResponseEntity<>(restaurantService.fetchAll(),HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> fetchRestaurant(@PathVariable UUID uuid) {
        try{
            return new ResponseEntity<>(restaurantService.fetchRestaurant(uuid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        UUID uuid = restaurantService.add(restaurantDTO);
        Map<String, UUID> response = new HashMap<>();
        response.put("id", uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
