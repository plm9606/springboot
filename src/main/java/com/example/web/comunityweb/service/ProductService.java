package com.example.web.comunityweb.service;

import com.example.web.comunityweb.domain.Product;

public interface ProductService {
    void addProduct(Product product);

    Product findByProductName(String name);
}
