package com.foodOrderproject.demo;




import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodOrderService {

    private final FoodOrderRepository repository;
    private final Validator validator;

    // 1. GET - Get all orders
    public List<FoodOrder> getAllOrders() {
        return repository.findAll();
    }

    // 2. GET BY ID
    public FoodOrder getOrderById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found"));
    }

    // 3. POST - Add order
    public FoodOrder addOrder(FoodOrder order) {

        order.setOrderId(null);

        return repository.save(order);
    }

    // 4. PUT - Complete order update
    public FoodOrder updateOrder(Long id, FoodOrder newOrder) {

        FoodOrder oldOrder = getOrderById(id);

        oldOrder.setCustomerName(newOrder.getCustomerName());
        oldOrder.setFoodItem(newOrder.getFoodItem());
        oldOrder.setQuantity(newOrder.getQuantity());
        oldOrder.setPrice(newOrder.getPrice());

        return repository.save(oldOrder);
    }

    // 5. DELETE - Delete order
    public String deleteOrder(Long id) {

        FoodOrder order = getOrderById(id);

        repository.delete(order);

        return "Order deleted successfully";
    }

    // 6. PATCH - Partial update
    public FoodOrder patchOrder(Long id, Map<String, Object> updates) {

        FoodOrder order = getOrderById(id);

        updates.forEach((key, value) -> {

            switch (key) {

                case "customerName":
                    order.setCustomerName((String) value);
                    break;

                case "foodItem":
                    order.setFoodItem((String) value);
                    break;

                case "quantity":
                    order.setQuantity(
                            value == null ? null :
                            ((Number) value).intValue());
                    break;

                case "price":
                    order.setPrice(
                            value == null ? null :
                            new BigDecimal(value.toString()));
                    break;

                default:
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Invalid field: " + key);
            }
        });

        // Validate the updated object
        var violations = validator.validate(order);

        if (!violations.isEmpty()) {

            String errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, errors);
        }

        return repository.save(order);
    }
}
