package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner{
    private  final ProductStorage productStorage;
    private  final OrderStorage orderStorage;
    private  final OrderService orderService;

    public Main(ProductStorage productStorage, OrderStorage orderStorage,OrderService orderService){
        this.productStorage = productStorage;
        this.orderStorage = orderStorage;
        this.orderService = orderService;
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        String miejscezamowienia="warszawa 30 lecia";
        Product product1 = new Product(1,"Melon",3,3.20);
        Product product2 = new Product(2,"Marakuja",2,3.20);
        Product product3 = new Product(3,"XDDD",4,3.20);
        productStorage.addProduct(product1);
        productStorage.addProduct(product2);

        System.out.println("Miejsce zamowienia: " + miejscezamowienia);
        Order order1 = new Order(1,1,List.of(product1,product2),Status.NOWE,miejscezamowienia);

        Order order2 = orderService.order(2,2,List.of(product3,product2),miejscezamowienia);
        System.out.println(productStorage.getAllProducts());
        System.out.println(orderStorage.getAllOrders());
        System.out.println(orderService.ustawieniewrealizacji(2));
        System.out.println(orderService.checkzamowienie(2));
        System.out.println("////////////");
        System.out.println(orderService.cancelzamowienie(2));
        System.out.println("////////////");
        System.out.println(orderService.checkzamowienie(2));
        System.out.println("////////////");
        System.out.println(orderService.ustawieniewrealizacji(2));
        System.out.println("////////////");
        System.out.println(orderService.checkzamowienie(2));
        System.out.println("////////////");
        System.out.println(orderService.potwierdzenieDostarczenie(2));
        System.out.println("////////////");
        System.out.println(orderService.checkzamowienie(2));
    }
}
