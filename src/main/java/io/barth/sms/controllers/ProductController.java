package io.barth.sms.controllers;

import io.barth.sms.entity.Product;
import io.barth.sms.entity.User;
import io.barth.sms.service.ProductServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product/")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final ProductServiceImp productServiceImp;

    public ProductController(ProductServiceImp productServiceImp) {
        this.productServiceImp = productServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        logger.info("Received get request from {}", createdBy);
        return new ResponseEntity<>(productServiceImp.getProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        Product newProduct = productServiceImp.createProduct(product, createdBy);
        logger.info("New product has been created {}", createdBy);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
}
