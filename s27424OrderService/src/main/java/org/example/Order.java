package org.example;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private List<Product> productList;
    private Status status;
    private String miejscedostawy;





    // Getters and setters


    public Order(int orderId, int customerId, List<Product> productList, Status status, String miejscedostawy) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productList = productList;
        this.status = status;
        this.miejscedostawy = miejscedostawy;
    }

    public List<Product> getProductList() {
        return productList;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMiejscedostawy() {
        return miejscedostawy;
    }



    public int getId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id_order='" + orderId  + '\'' +
                "id_client='" + customerId  + '\'' +
                ", ProductList='" + productList + '\'' +
                ", Status='" + status + '\'' +
                ", MiejsceDostawy=" + miejscedostawy +
                '}';
    }
}
