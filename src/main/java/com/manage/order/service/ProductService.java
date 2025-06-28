package com.manage.order.service;

import com.manage.order.entity.Product;
import com.manage.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void addProducts(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
