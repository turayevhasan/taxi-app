package uz.pdp.back.controller;

import lombok.Getter;
import uz.pdp.back.controller.contracts.OrderController;
import uz.pdp.back.controller.contracts.UserController;
import uz.pdp.back.model.Car;
import uz.pdp.back.model.Order;
import uz.pdp.back.model.User;
import uz.pdp.back.payload.CarDTO;
import uz.pdp.back.payload.OrderAddDTO;
import uz.pdp.back.payload.OrderDTO;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.repository.CarRepositoryImpl;
import uz.pdp.back.repository.OrderRepositoryImpl;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.CarRepository;
import uz.pdp.back.repository.contracts.OrderRepository;
import uz.pdp.back.repository.contracts.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderControllerImpl implements OrderController {
    @Getter
    private static final OrderController instance = new OrderControllerImpl();

    private OrderControllerImpl() {
    }

    private static final OrderRepository orderRepository = OrderRepositoryImpl.getInstance();
    private static final CarRepository carRepository = CarRepositoryImpl.getInstance();
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final UserController userController = UserControllerImpl.getInstance();

    @Override
    public List<OrderDTO> getOrdersByDriverId(UUID driverId) {
        List<Order> orders = orderRepository.getAllOrders();

        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            if (order.getCar().getDriver().getId().equals(driverId) && order.isActive()) {
                Car car = order.getCar();
                UserDTO driverDTO = userController.findById(car.getDriver().getId());
                CarDTO carDTO = new CarDTO(car.getName(), driverDTO, car.getNumber(), car.getType());

                User client = order.getClient();
                UserDTO userDTO = new UserDTO(client.getId(), client.getName(), client.getAge(), client.getPhone(), client.getEmail(), client.getRole(), client.getBalance());
                orderDTOS.add(new OrderDTO(order.getId(), order.getFrom(), order.getTo(), carDTO, userDTO, order.getAmount(), order.isActive()));
            }

        }
        return orderDTOS;
    }

    @Override
    public void save(OrderAddDTO orderAddDTO) {
        User client = userRepository.getById(orderAddDTO.client().id());
        Car car = carRepository.findByDriverId(orderAddDTO.carDTO().driverDTO().id());

        Order order = new Order(orderAddDTO.from(), orderAddDTO.to(), car, client, orderAddDTO.amount());
        orderRepository.save(order);
    }

    @Override
    public void acceptOrder(UUID driverId) {
        boolean active = orderRepository.setActive(driverId);
        if (active) {
            System.out.println("Order is accepted ✅");
        } else {
            System.out.println("Order is not accepted ❌");
        }
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(UUID userId) {
        List<Order> orders = orderRepository.getAllOrders();

        if (orders == null || orders.isEmpty()) {
            throw new RuntimeException("You have no orders yet ❌");
        }
        List<OrderDTO> orderDTOS = new ArrayList<>();


        for (Order order : orders) {
            if (order.getClient().getId().equals(userId) && !order.isActive()) {
                Car car = order.getCar();
                UserDTO driverDTO = userController.findById(car.getDriver().getId());
                CarDTO carDTO = new CarDTO(car.getName(), driverDTO, car.getNumber(), car.getType());

                User client = order.getClient();
                UserDTO userDTO = new UserDTO(client.getId(), client.getName(), client.getAge(), client.getPhone(), client.getEmail(), client.getRole(), client.getBalance());
                orderDTOS.add(new OrderDTO(order.getId(), order.getFrom(), order.getTo(), carDTO, userDTO, order.getAmount(), order.isActive()));
            }
        }

        return orderDTOS;
    }
}
