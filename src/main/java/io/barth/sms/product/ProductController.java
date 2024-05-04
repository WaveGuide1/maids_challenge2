package io.barth.sms.product;

import io.barth.sms.authentication.AuthenticationController;
import io.barth.sms.exception.GeneralApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product")
public class ProductController {

    private final ProductServiceImp productServiceImp;

    public ProductController(ProductServiceImp productServiceImp) {
        this.productServiceImp = productServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(){
        try {
            return new ResponseEntity<>(productServiceImp.getProducts(), HttpStatus.OK);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        try {
            Product newProduct = productServiceImp.createProduct(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable Long id, @RequestBody Product product){
        return new ResponseEntity<>(productServiceImp.updateProduct(id, product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productServiceImp.getProduct(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id){
            productServiceImp.deleteProduct(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
