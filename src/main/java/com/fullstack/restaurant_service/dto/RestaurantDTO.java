package com.fullstack.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private UUID id;
    private String name;
    private String address;
    private String city;
    private String description;
}
