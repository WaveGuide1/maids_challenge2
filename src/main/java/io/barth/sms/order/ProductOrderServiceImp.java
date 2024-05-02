package io.barth.sms.order;

import io.barth.sms.client.Client;
import io.barth.sms.exception.ClientNotFoundException;
import io.barth.sms.exception.GeneralApplicationException;
import io.barth.sms.exception.OrderNotFoundException;
import io.barth.sms.exception.ProductNotFoundException;
import io.barth.sms.product.Product;
import io.barth.sms.client.ClientRepository;
import io.barth.sms.product.ProductRepository;
import io.barth.sms.user.User;
import io.barth.sms.utilities.ProductOrderBusinessLogic;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductOrderServiceImp implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public ProductOrderServiceImp(ProductOrderRepository productOrderRepository,
                                  ProductRepository productRepository, ClientRepository clientRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public ProductOrder createProductOrder(Long productId, ProductOrder productOrder, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        // Checking whether there is enough product
        int quantity = ProductOrderBusinessLogic.quantityLogic(
                product.getQuantity(), productOrder.getQuantity()
        );

        var client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ClientNotFoundException("Register as client"));

        product.setQuantity(quantity);
        productRepository.save(product);
        productOrder.setProduct(product);
        ProductOrder newOrder = productOrderRepository.save(productOrder);
        client.getProductOrder().add(newOrder);
        clientRepository.save(client);
        return newOrder;

    }

    @Override
    @Transactional
    public ProductOrder updateProductOrder(Long orderId, ProductOrder productOrder, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var client = user.getClient();
        ProductOrder oldProductOrder = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No order with the given id"));

        Product product = productRepository.findById(oldProductOrder.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("No available product"));

        int quantity = ProductOrderBusinessLogic.quantityLogic(
                product.getQuantity(), oldProductOrder.getQuantity(), productOrder.getQuantity()
        );

        for (ProductOrder item: client.getProductOrder()){
            if(orderId.equals(item.getId())){
                break;
            } else {
                throw new OrderNotFoundException("You have no such order");
            }
        }
        product.setQuantity(quantity);
        productRepository.save(product);

        oldProductOrder.setId(orderId);
        oldProductOrder.setQuantity(productOrder.getQuantity());

        return productOrderRepository.save(oldProductOrder);
    }

    @Override
    @Transactional
    public String confirmOrder(Long orderId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var client1 = user.getClient();

        ProductOrder productOrder = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new ProductNotFoundException("No order with the given id"));

        var client = clientRepository.findById(client1.getId())
                .orElseThrow(() -> new ClientNotFoundException("No available client"));

        for(ProductOrder clientOrder: client.getProductOrder()){
            if (clientOrder.getId().equals(orderId)) {
                break;
            } else {
                throw new OrderNotFoundException("You did not order the product");
            }
        }
        String productName = productOrder.getProduct().getProductName();
        client.getProductOrder().remove(productOrder);
        clientRepository.save(client);
        productOrderRepository.deleteById(orderId);
        return productName;
    }

    @Override
    public String cancelOrder(Long orderId, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var client1 = user.getClient();
        ProductOrder productOrder = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No product with the given id"));

        var client = clientRepository.findById(client1.getId())
                .orElseThrow(() -> new ClientNotFoundException("No available client"));

        Product product = productRepository.findById(productOrder.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("No available product"));

        for(ProductOrder oldOrder: client.getProductOrder()){
            if(oldOrder.getId().equals(orderId)){
                break;
            } else {
                throw new GeneralApplicationException("You did not order the product");
            }
        }
        int quantity = ProductOrderBusinessLogic.cancellationQuantity(product.getQuantity(),
                productOrder.getQuantity());

        product.setQuantity(quantity);
        productRepository.save(product);
        client.getProductOrder().remove(productOrder);
        clientRepository.save(client);
        productOrderRepository.deleteById(orderId);
        return "Order has been cancelled";
    }

    @Override
    public List<ProductOrder> getProductOrder(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var client = user.getClient();

        if(client.getProductOrder() == null)
            throw new OrderNotFoundException("You have no order");
        return clientRepository.findById(user.getId()).orElseThrow().getProductOrder();
    }

    @Override
    public Optional<ProductOrder> getProductOrderById(Long orderId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var client = user.getClient();
        if(client.getId().equals(orderId)){
            return Optional.ofNullable(productOrderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Client has no such order")));
        } else {
            throw new GeneralApplicationException("You did not order the product");
        }

    }

}
