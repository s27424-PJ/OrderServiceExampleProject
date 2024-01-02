package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void productExists() {
        Product product = new Product(1, "Kokos", 3, 3.20);
        assertThat(product).isNotNull();
    }
    @Test
    void productExistsv2() {
        Product product = new Product(1, "Kokos", 99, 23.20);
        assertNotNull(product);
    }
    @Test
    void productcantCreate() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->new Product(1, "Kokos", 0, 3.20));
    }
}