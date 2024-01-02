package org.example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.time.LocalDate;
@Service
public class OrderService {
    private final ProductStorage productStorage;
    private final OrderStorage orderStorage;
    public OrderService(ProductStorage productStorage, OrderStorage orderStorage) {
        this.productStorage = productStorage;
        this.orderStorage = orderStorage;
    }
    public Order order(int id_client , int id_order, List<Product> productList, String miejscezamowienia){
                if(productList.isEmpty()) {
                   return null;
                }
                for(Product product : productList){
                if(!productExists(product.getId())){
                    throw new RuntimeException("Produkt z listy nie jest dodany do bazy produktów");
                }
                }
                Order order = new Order(id_order,id_client,productList ,Status.NOWE, miejscezamowienia);
                orderStorage.addOrder(order);
                return order;

    }
    public boolean productExists(int id) {
        Product product = productStorage.getProductbyId(id);
        if (product != null) {
            return true;
        } else {
            return false;
        }
    }
    public OrderStatus checkzamowienie(int id){
        List<Order> orderList= orderStorage.getAllOrders();

        for(Order order : orderList) {
            if(order.getId()==id){
                OrderStatus orderStatus = new OrderStatus(order.getStatus(),order.getProductList());
                return orderStatus;
            }

        }
        throw new RuntimeException("niema takiego zamówienia");
    }
    public StatusMassage cancelzamowienie(int id){
        List<Order> orderList= orderStorage.getAllOrders();
        for(Order order : orderList) {

           if(order.getId()==id) {
               if (order.getStatus().equals(Status.W_REALIZACJI)) {
                   throw new RuntimeException("Zamowienie jest w realziacji nie mozna anulowac");
               }
               order.setStatus(Status.ANULOWANE);
               return new StatusMassage("Anulowane", "Zamówienie zostało pomyślnie anulowane.");
           }
            }
        throw new RuntimeException("niema takiego zamówienia");
        }
    public StatusMassage ustawieniewrealizacji(int id){
        List<Order> orderList= orderStorage.getAllOrders();
        for(Order order : orderList) {
            if(order.getId()==id) {
                if (order.getStatus().equals(Status.DOSTARCZONE)){
                    throw new RuntimeException("Zamowienie jest juz dostarczone");
                }
                order.setStatus(Status.W_REALIZACJI);
                return new StatusMassage("W  realizacji", "Zamówienie zostało pomyślnie ustawione na w realizacji");
            }
        }
        throw new RuntimeException("niema takiego zamówienia");
    }
    public StatusMassage potwierdzenieDostarczenie(int id){
        List<Order> orderList= orderStorage.getAllOrders();
        for(Order order : orderList) {
            if (order.getStatus().equals(Status.ANULOWANE)){
                throw new RuntimeException("Zamowienie bylo wczesniej anulowane");
            }
            if(order.getId()==id) {
                order.setStatus(Status.DOSTARCZONE);
                return new StatusMassage("Dostarczone", "Zamówienie zostało dostarczone");
            }
        }
        throw new RuntimeException("niema takiego zamówienia");
    }
}
