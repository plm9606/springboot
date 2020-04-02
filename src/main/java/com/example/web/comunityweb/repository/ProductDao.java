package com.example.web.comunityweb.repository;

import com.example.web.comunityweb.domain.Product;

public interface ProductDao {
    void insertProduct(Product product);
    Product findByProductName(String name);
}
