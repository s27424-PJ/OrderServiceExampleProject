package org.example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Component
public class ProductStorage {

    private List<Product> products= new ArrayList<>();


    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductbyId(int id) {
        for (Product product : products) {
            if (product.getId()==id) {
                return product;
            }
        }
        return null;
    }

}
