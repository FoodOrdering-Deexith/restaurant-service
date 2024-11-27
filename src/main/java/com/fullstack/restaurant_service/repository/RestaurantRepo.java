package com.fullstack.restaurant_service.repository;

import com.fullstack.restaurant_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, UUID> {
}
