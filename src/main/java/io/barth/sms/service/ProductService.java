package io.barth.sms.service;

import io.barth.sms.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProducts();

    public Product createProduct(Product product, String username);

    public Product getProduct(Long id);

    public  Product updateProduct(Product product);

    public void deleteProduct(Long id);
}
