package com.fullstack.restaurant_service.mapper;

import com.fullstack.restaurant_service.dto.RestaurantDTO;
import com.fullstack.restaurant_service.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);
    Restaurant restaurantDTOToRestaurant(RestaurantDTO restaurantDTO);

}
