package uz.pdp.back.controller.contracts;

import uz.pdp.back.payload.OrderAddDTO;
import uz.pdp.back.payload.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderController {
    List<OrderDTO> getOrdersByDriverId(UUID driverId);

    void save(OrderAddDTO orderAddDTO);

    void acceptOrder(UUID driverId);

    List<OrderDTO> getOrdersByUserId(UUID userId);
}
