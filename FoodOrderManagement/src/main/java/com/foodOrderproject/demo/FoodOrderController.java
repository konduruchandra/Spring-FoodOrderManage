package com.foodOrderproject.demo;



import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class FoodOrderController {

    private final FoodOrderService service;

    // 1. GET - Get all orders
    @GetMapping
    public List<FoodOrder> getAllOrders() {

        return service.getAllOrders();
    }

    // 2. GET BY ID
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Long id) {

        return service.getOrderById(id);
    }

    // 3. POST - Add new order
    @PostMapping
    public FoodOrder addOrder(@Valid @RequestBody FoodOrder order) {

        return service.addOrder(order);
    }

    // 4. PUT - Update complete order
    @PutMapping("/{id}")
    public FoodOrder updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody FoodOrder order) {

        return service.updateOrder(id, order);
    }

    // 5. DELETE - Delete order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {

        return service.deleteOrder(id);
    }

    // 6. PATCH - Partial update
    @PatchMapping("/{id}")
    public FoodOrder patchOrder(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return service.patchOrder(id, updates);
    }
}
