package io.barth.sms.controllers;

import io.barth.sms.entity.ProductOrder;
import io.barth.sms.serviceImp.ProductOrderServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/orders")
public class ProductOrderController {

    private final ProductOrderServiceImp productOrderServiceImp;

    public ProductOrderController(ProductOrderServiceImp productOrderServiceImp) {
        this.productOrderServiceImp = productOrderServiceImp;
    }

    @GetMapping("/")
    public List<ProductOrder> getAllOrderItems() {
        return productOrderServiceImp.getProductOrder();
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductOrder> createOrderItem(@PathVariable Long id, @RequestBody ProductOrder order) {
        try {
            ProductOrder createdOrder = productOrderServiceImp.createProductOrder(id, order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOrder> updateOrder(@PathVariable Long id, @RequestBody ProductOrder order) {
        Optional<ProductOrder> oldOrder = productOrderServiceImp.getProductOrderById(id);
        if (oldOrder.isPresent()) {
            ProductOrder currentOrder = oldOrder.get();
            currentOrder.setProduct(order.getProduct());
            currentOrder.setQuantity(order.getQuantity());
            ProductOrder updatedOrder = productOrderServiceImp.updateProductOrder(id, currentOrder);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrder> getOrderItemById(@PathVariable Long id) {
        Optional<ProductOrder> order = productOrderServiceImp.getProductOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        productOrderServiceImp.deleteProductOrder(id);
        return ResponseEntity.noContent().build();
    }
}
