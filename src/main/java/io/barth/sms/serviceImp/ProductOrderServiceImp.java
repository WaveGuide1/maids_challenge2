package io.barth.sms.serviceImp;

import io.barth.sms.entity.Client;
import io.barth.sms.entity.Product;
import io.barth.sms.entity.ProductOrder;
import io.barth.sms.repository.ClientRepository;
import io.barth.sms.repository.ProductOrderRepository;
import io.barth.sms.repository.ProductRepository;
import io.barth.sms.service.ProductOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderServiceImp implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public ProductOrderServiceImp(ProductOrderRepository productOrderRepository,
                                  ClientRepository clientRepository,
                                  ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductOrder createProductOrder(Long clientId, Long productId, ProductOrder productOrder) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + "not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " +
                        productId + "not found"));

        // Checking whether there is enough product
        int productQuantity = product.getQuantity();
        int orderQuantity = productOrder.getQuantity();
        if (productQuantity == 0){
            throw new RuntimeException("We have limited product for now. Come back later");

        } else if (productQuantity < orderQuantity) {
            throw new RuntimeException("We have limited product for now. Please reduce the order " +
                    "quantity or come back later");
        } else {
            product.setQuantity(productQuantity - orderQuantity);
            productRepository.save(product);
        }

        productOrder.setClient(client);
        productOrder.setProduct(product);

        ProductOrder newProductOrder = productOrderRepository.save(productOrder);
        client.getProductOrder().add(newProductOrder);
        return newProductOrder;
    }

    @Override
    public ProductOrder updateProductOrder(Long clientId, Long orderId, ProductOrder productOrder) {
        ProductOrder oldProductOrder = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("No order with id of " + orderId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("No client with id of " + clientId));

        if(client.getProductOrder().isEmpty())
            return null;

        for (ProductOrder item: client.getProductOrder()){
            if(orderId.equals(item.getId())){
                oldProductOrder.setId(orderId);
                oldProductOrder.setQuantity(productOrder.getQuantity());
                break;
            }
        }
        return productOrderRepository.save(oldProductOrder);
    }

    @Override
    public List<ProductOrder> getProductOrder() {
        return null;
    }

    @Override
    public Optional<ProductOrder> getProductOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteProductOrder(Long id) {

    }
}
