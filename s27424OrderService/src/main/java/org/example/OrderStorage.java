package org.example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
public class OrderStorage {

    private List<Order> orders= new ArrayList<>();


    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAllOrders() {
        return orders;
    }


    public Order getOrderbyID(int id) {
        for (Order order : orders) {
            if (order.getId()==id) {
                return order;
            }
        }
        return null;
    }

}
