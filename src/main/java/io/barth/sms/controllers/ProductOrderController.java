package io.barth.sms.controllers;

import io.barth.sms.entity.Client;
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

    @GetMapping("/client/{clientId}")
    public List<ProductOrder> getAllOrderItems(@PathVariable Long clientId) {
        List<ProductOrder> order = productOrderServiceImp.getProductOrder(clientId);
        if(order.isEmpty())
            return null;
        return order;
    }

    // Save an order
    @PostMapping("/client/{clientId}/product/{productId}")
    public ResponseEntity<ProductOrder> createOrderItem(@PathVariable Long clientId,
                                                        @PathVariable Long productId,
                                                        @RequestBody ProductOrder order) {
        try {
            ProductOrder createdOrder = productOrderServiceImp.createProductOrder(clientId, productId, order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    // Confirm an order
    @PostMapping("/client/{clientId}/order/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable Long clientId,
                                                        @PathVariable Long orderId) {
        try {
            String confirmOrder = productOrderServiceImp.confirmOrder(clientId, orderId);
            if(confirmOrder == null) {
                return new ResponseEntity<>("This product has been delivered to you", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Product with product name " + confirmOrder + " has been delivered to you", HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/client/{clientId}/order/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long clientId,
                                               @PathVariable Long orderId) {
        try {
            String cancelOrder = productOrderServiceImp.cancelOrder(clientId, orderId);
            if(cancelOrder == null) {
                return new ResponseEntity<>("You did not order this product", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Order with product name " + cancelOrder + " have been cancelled", HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/client/{clientId}/order/{orderId}")
    public ResponseEntity<ProductOrder> updateOrder(@PathVariable Long clientId,
                                                    @PathVariable Long orderId,
                                                    @RequestBody ProductOrder order) {

        try {
            ProductOrder updatedOrder = productOrderServiceImp.updateProductOrder(clientId, orderId, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/client/{clientId}/order/{orderId}")
    public ResponseEntity<ProductOrder> getOrderItemById(@PathVariable Long clientId,
                                                         @PathVariable Long orderId) {
        Optional<ProductOrder> order = productOrderServiceImp.getProductOrderById(clientId, orderId);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
