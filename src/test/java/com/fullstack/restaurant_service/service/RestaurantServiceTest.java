package com.fullstack.restaurant_service.service;

import com.fullstack.restaurant_service.dto.RestaurantDTO;
import com.fullstack.restaurant_service.entity.Restaurant;
import com.fullstack.restaurant_service.repository.RestaurantRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @InjectMocks
    RestaurantService restaurantService;

    @Mock
    RestaurantRepo restaurantRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAll() {
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Restaurant 1", "Test Address", "Frisco", "Description");
        List<Restaurant> restaurantList = new ArrayList<>(List.of(restaurant));
        when(restaurantRepo.findAll()).thenReturn(restaurantList);
        List<RestaurantDTO> restaurantDTO = restaurantService.fetchAll();
        assertEquals(restaurantDTO.get(0).getName(), "Restaurant 1");
        assertEquals(restaurantDTO.get(0).getAddress(), "Test Address");
    }

    @Test
    public void testFetchRestaurant() {
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Restaurant 1", "Test Address", "Frisco", "Description");
        when(restaurantRepo.findById(any())).thenReturn(Optional.of(restaurant));
        RestaurantDTO restaurantDTO = restaurantService.fetchRestaurant(any());
        assertEquals(restaurantDTO.getName(), "Restaurant 1");
        assertEquals(restaurantDTO.getAddress(), "Test Address");
        verify(restaurantRepo, times(1)).findById(any());
    }

    @Test
    public void testAdd() {
        UUID uuid = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(uuid, "Restaurant 1", "Test Address", "Frisco", "Description");
        when(restaurantRepo.saveAndFlush(any())).thenReturn(restaurant);
        UUID uuidRes = restaurantService.add(any());
        assertEquals(uuidRes, uuid);
    }
}
