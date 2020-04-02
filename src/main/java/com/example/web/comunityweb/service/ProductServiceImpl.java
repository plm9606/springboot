package com.example.web.comunityweb.service;

import com.example.web.comunityweb.domain.Product;
import com.example.web.comunityweb.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public void addProduct(Product product) {
        productDao.insertProduct(product);
    }

    @Override
    public Product findByProductName(String name) {
        return productDao.findByProductName(name);
    }
}
