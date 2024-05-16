package uz.pdp.back.repository;

import lombok.Getter;
import uz.pdp.back.model.Order;
import uz.pdp.back.repository.contracts.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderRepositoryImpl implements OrderRepository {
    @Getter
    private static final OrderRepository instance = new OrderRepositoryImpl();

    private OrderRepositoryImpl() {
    }

    private static final String orderDataUrl = "database/order_data.txt";
    private static final DataSaverGetter<Order> dataSaverGetter = new DataSaverGetter<>();

    @Override
    public List<Order> getAllOrders() {
        return dataSaverGetter.getAllData(orderDataUrl);
    }

    @Override
    public void save(Order order) {
        List<Order> orders = dataSaverGetter.getAllData(orderDataUrl);
        orders.add(order);
        dataSaverGetter.writeDataToFile(orders, orderDataUrl);
    }

    @Override
    public boolean setActive(UUID driverId) {
        List<Order> orders = dataSaverGetter.getAllData(orderDataUrl);
        for (Order order : orders) {
            if(order.getCar().getDriver().getId().equals(driverId)) {
                order.setActive(false);
                dataSaverGetter.writeDataToFile(orders, orderDataUrl);
                return true;
            }
        }
        return false;
    }


}
