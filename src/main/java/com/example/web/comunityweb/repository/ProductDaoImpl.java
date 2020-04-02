package com.example.web.comunityweb.repository;

import com.example.web.comunityweb.domain.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    private Map<String, Product> productMap = new HashMap<>();

    @Override
    public void insertProduct(Product product) {
        System.out.println("insert "+product.getName());
        productMap.put(product.getName(), product);
    }

    @Override
    public Product findByProductName(String name) {
        System.out.println("find "+name);
        return productMap.get(name);
    }
}
