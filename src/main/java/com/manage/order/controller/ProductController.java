package com.manage.order.controller;

import com.manage.order.entity.Product;
import com.manage.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProducts(@RequestBody List<Product> productList) {
        productService.addProducts(productList);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<Product>> addProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }
}
