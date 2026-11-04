package com.foodOrderproject.demo;




import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository
        extends JpaRepository<FoodOrder, Long> {

}

