package com.example.web.comunityweb.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String name;
    private int price;
}
