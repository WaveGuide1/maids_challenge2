package io.barth.sms.controllers;

import io.barth.sms.entity.Product;
import io.barth.sms.service.ProductServiceImp;
import io.barth.sms.utilities.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        logger.info("Received get request");
        return new ResponseEntity<>(productServiceImp.getProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request){
        Product product = new Product(request);

    }
}
