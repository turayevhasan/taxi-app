package uz.pdp.back.repository.contracts;

import uz.pdp.back.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getAllOrders();
    void save(Order order);
    boolean setActive(UUID oderId);
}
