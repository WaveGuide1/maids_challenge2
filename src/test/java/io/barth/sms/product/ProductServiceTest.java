package io.barth.sms.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImp productServiceImp;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProducts(){
        List<Product> product = new ArrayList<>();
        product.add(new Product());
        when(productRepository.findAll()).thenReturn(product);

        List<Product> returnProducts = productServiceImp.getProducts();

        Assertions.assertEquals(1, returnProducts.size());
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setQuantity(20);
        product.setPrice(100);
        product.setCreatedDate(LocalDateTime.now());

        when(productRepository.save(any())).thenReturn(product);
        Product savedProduct = productServiceImp.createProduct(product);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals("Test Product", savedProduct.getProductName());
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setProductName("Existing Product");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setProductName("Updated Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product returnedProduct = productServiceImp.updateProduct(productId, updatedProduct);
        Assertions.assertEquals("Updated Product", returnedProduct.getProductName());
    }

    @Test
    public void testGetProduct() {
        Long productId = 1L;
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product returnedProduct = productServiceImp.getProduct(productId);
        Assertions.assertNotNull(returnedProduct);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));
        productServiceImp.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }
}
