package io.barth.sms.service;

import io.barth.sms.entity.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface ProductOrderService {

    public ProductOrder createProductOrder(Long clientId, Long productId, ProductOrder productOrder);

    public ProductOrder updateProductOrder(Long clientId, Long productId, ProductOrder productOrder);

    public ProductOrder

    public ProductOrder cancelOrder(Long clientId, Long orderId);

    public List<ProductOrder> getProductOrder(Long clientId);

    public Optional<ProductOrder> getProductOrderById(Long clientId, Long orderId);

    public void deleteProductOrder(Long id);
}
