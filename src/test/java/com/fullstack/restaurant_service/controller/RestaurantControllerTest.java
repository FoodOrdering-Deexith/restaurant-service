package com.fullstack.restaurant_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.restaurant_service.dto.RestaurantDTO;
import com.fullstack.restaurant_service.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    RestaurantService restaurantService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void testFetchAllRestaurant() throws Exception {
        UUID uuid = UUID.randomUUID();
        RestaurantDTO restaurantDTO = new RestaurantDTO(uuid, "Restaurant 1", "Test address", "Frisco", "Test Description");

        // Mocking the service behavior
        when(restaurantService.fetchRestaurant(any())).thenReturn(restaurantDTO);

        mockMvc.perform(get("/v1/restaurant/" + uuid.toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Restaurant 1"));
    }

    @Test
    public void testFetchAllRestaurants() throws Exception {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        RestaurantDTO restaurantDTO1 = new RestaurantDTO(UUID.randomUUID(), "Restaurant 1", "Test address", "Frisco", "Test Description 1");
        RestaurantDTO restaurantDTO2 = new RestaurantDTO(UUID.randomUUID(), "Restaurant 2", "Test address", "Plano", "Test Description 2");

        restaurantDTOList.add(restaurantDTO1);
        restaurantDTOList.add(restaurantDTO2);

        when(restaurantService.fetchAll()).thenReturn(restaurantDTOList);

        mockMvc.perform(get("/v1/restaurant"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Restaurant 1"))
                .andExpect(jsonPath("$[1].name").value("Restaurant 2"));
    }

    @Test
    public void testCreateRestaurant() throws Exception {
        RestaurantDTO restaurantDTO = new RestaurantDTO(UUID.randomUUID(),"Restaurant 1", "Test address", "Frisco", "Test Description");
        when(restaurantService.add(any(RestaurantDTO.class))).thenReturn(UUID.randomUUID());
        mockMvc.perform(post("/v1/restaurant").content(new ObjectMapper().writeValueAsString(restaurantDTO)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()));
    }


}
