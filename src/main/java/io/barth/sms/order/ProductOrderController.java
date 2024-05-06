package io.barth.sms.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<ProductOrder> getAllOrderItems(Principal connectedUser) {
        return productOrderServiceImp.getProductOrder(connectedUser);
    }

    // Save an order
    @PostMapping("/product/{productId}")
    public ResponseEntity<ProductOrder> createOrderItem(@Valid @PathVariable Long productId,
                                                        @RequestBody ProductOrder order,
                                                        Principal connectedUser) {
        ProductOrder createdOrder = productOrderServiceImp.createProductOrder(productId, order, connectedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Confirm an order
    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId, Principal connectedUser) {
            String confirmOrder = productOrderServiceImp.confirmOrder(orderId, connectedUser);

            return new ResponseEntity<>("Product with product name " + confirmOrder + " has been delivered to you", HttpStatus.OK);

    }

    // Cancel an Order
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, Principal connectedUser) {
            String cancelOrder = productOrderServiceImp.cancelOrder(orderId, connectedUser);

            return new ResponseEntity<>("Order with product name " + cancelOrder + " have been cancelled", HttpStatus.OK);

    }

    // Update an Order
    @PutMapping("/{orderId}")
    public ResponseEntity<ProductOrder> updateOrder(@Valid @PathVariable Long orderId,
                                                    @RequestBody ProductOrder order,
                                                    Principal connectedUser) {

            ProductOrder updatedOrder = productOrderServiceImp.updateProductOrder(orderId, order, connectedUser);
            return ResponseEntity.ok(updatedOrder);
    }

    // Get specific order
    @GetMapping("/{orderId}")
    public ResponseEntity<ProductOrder> getOrderItemById(@PathVariable Long orderId,
                                                         Principal connectedUser) {
        Optional<ProductOrder> order = productOrderServiceImp.getProductOrderById(orderId, connectedUser);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
