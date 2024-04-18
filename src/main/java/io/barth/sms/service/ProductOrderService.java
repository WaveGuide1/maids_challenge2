package io.barth.sms.service;

import io.barth.sms.entity.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface ProductOrderService {

    public ProductOrder createProductOrder(Long id, ProductOrder productOrder);

    public ProductOrder updateProductOrder(Long id, ProductOrder productOrder);

    public List<ProductOrder> getProductOrder();

    public Optional<ProductOrder> getProductOrderById(Long id);

    public void deleteProductOrder(Long id);
}
