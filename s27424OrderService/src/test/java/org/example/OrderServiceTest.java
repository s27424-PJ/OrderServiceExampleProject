package org.example;

import org.assertj.core.api.Assert;
import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ProductStorage productStorage;
    @Mock
    private OrderStorage orderStorage;
    @InjectMocks
    private OrderService orderService;
    @Test
    void ordernotnull() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        assertNotNull(order);
    }

    @Test
    void ordernull() {
        Order order =  orderService.order(1,1,List.of(),"Katowice");
        assertNull(order);
    }
    @Test
    void productcantCreate() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->new Product(1, "Kokos", 0, 3.20));
    }
    @Test
    void checkzamowienie() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        Order order2 = orderService.order(1,2,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order,order2));
        OrderStatus check = orderService.checkzamowienie(1);
        System.out.println(check);
        assertThat(check).isNotNull();
    }
    @Test
    void falsecheckzamowienie() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "sokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order1 = orderService.order(1,3,List.of(product,product2),"Katowice");
        Order order2 = orderService.order(1,4,List.of(product,product2),"Katowice");
        Order order3 = orderService.order(1,2,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order1,order2,order3));
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->orderService.checkzamowienie(2));
    }
    @Test
    void falsecheckzamowienie2whenlistblank() {

        when(orderStorage.getAllOrders()).thenReturn(List.of());
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->orderService.checkzamowienie(2));
    }
    @Test
    void cancelzamowienie() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));
        StatusMassage statusMassage = orderService.cancelzamowienie(1);
        assertThat(statusMassage).isNotNull();
    }
    @Test
    void falsecancelzamowienie() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.cancelzamowienie(2))
                        .withMessage("niema takiego zamówienia");

    }
    @Test
    void falsecancelzamowienieblanklist() {

        when(orderStorage.getAllOrders()).thenReturn(List.of());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.cancelzamowienie(1))
                .withMessage("niema takiego zamówienia");

    }
    @Test
    void falsecancelzamowieniebowrealizacji() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));
        orderService.ustawieniewrealizacji(1);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.cancelzamowienie(1))
                .withMessage("Zamowienie jest w realziacji nie mozna anulowac");

    }



    @Test
    void ustawieniewrealizacji() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));
        StatusMassage real = orderService.ustawieniewrealizacji(1);
        assertThat(real).isNotNull();

    }
    @Test
    void falseustawieniewrealizacji() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.ustawieniewrealizacji(2))
                .withMessage("niema takiego zamówienia");

    }
    @Test
    void falseustawieniewrealizacjibodostarczone() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));
        orderService.potwierdzenieDostarczenie(1);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.ustawieniewrealizacji(1))
                .withMessage("Zamowienie jest juz dostarczone");

    }
    @Test
    void falseustawieniewrealziacjieblanklist() {

        when(orderStorage.getAllOrders()).thenReturn(List.of());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.ustawieniewrealizacji(1))
                .withMessage("niema takiego zamówienia");

    }
    @Test
    void potwierdzenieDostarczenie() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));
        StatusMassage real = orderService.potwierdzenieDostarczenie(1);
        assertThat(real).isNotNull();
    }
    @Test
    void falsepotwierdzenieDostarczenia() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.potwierdzenieDostarczenie(2))
                .withMessage("niema takiego zamówienia");

    }
    @Test
    void falseustawieniewrealizacjiboanulowane() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        Product product2 = new Product(2, "Kokos", 3, 3.20);
        when(productStorage.getProductbyId(1)).thenReturn(product);
        when(productStorage.getProductbyId(2)).thenReturn(product2);
        Order order = orderService.order(1,1,List.of(product,product2),"Katowice");
        when(orderStorage.getAllOrders()).thenReturn(List.of(order));
        orderService.cancelzamowienie(1);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() ->orderService.potwierdzenieDostarczenie(1))
                .withMessage("Zamowienie bylo wczesniej anulowane");

    }
}