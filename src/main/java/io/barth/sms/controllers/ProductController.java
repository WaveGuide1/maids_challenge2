package io.barth.sms.controllers;

import io.barth.sms.entity.Product;
import io.barth.sms.serviceImp.ProductServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final ProductServiceImp productServiceImp;

    public ProductController(ProductServiceImp productServiceImp) {
        this.productServiceImp = productServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(){
        logger.info("Received get request from");
        return new ResponseEntity<>(productServiceImp.getProducts(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product newProduct = productServiceImp.createProduct(product);
        logger.info("New product has been created");
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        logger.info("Old product has been Updated");
        return new ResponseEntity<>(productServiceImp.updateProduct(id, product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        try {
            logger.info("A product was queried");
            return new ResponseEntity<>(productServiceImp.getProduct(id), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id){
        try {
            productServiceImp.deleteProduct(id);
            logger.info("A product with an id " + id + " was deleted");
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
}
