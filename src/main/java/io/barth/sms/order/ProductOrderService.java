package io.barth.sms.order;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductOrderService {

    public ProductOrder createProductOrder(Long productId, ProductOrder productOrder, Principal connectedUser);

    public ProductOrder updateProductOrder(Long productId, ProductOrder productOrder, Principal connectedUser);

    public String confirmOrder(Long orderId, Principal connectedUser);

    public String cancelOrder(Long orderId, Principal connectedUser);

    public List<ProductOrder> getProductOrder(Principal connectedUser);

    public Optional<ProductOrder> getProductOrderById(Long orderId, Principal connectedUser);

}
