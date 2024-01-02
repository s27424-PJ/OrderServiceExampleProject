package org.example;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus {
    private Status status;

    public OrderStatus(Status status, List<Product> products) {
        this.status = status;
        this.products = products;
    }

    private List<Product> products;


    public List<Product> getProducts() {
        return products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    @Override
    public String toString() {
        return "Zamowienie: " +
                "status='" + status + '\'' +
                ", ilosc='" + products + '\'' +

                '}';
    }

}
