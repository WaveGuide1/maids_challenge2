package io.barth.sms.serviceImp;

import io.barth.sms.entity.Client;
import io.barth.sms.entity.ProductOrder;
import io.barth.sms.repository.ClientRepository;
import io.barth.sms.repository.ProductOrderRepository;
import io.barth.sms.repository.ProductRepository;
import io.barth.sms.service.ProductOrderService;
import io.barth.sms.service.ProductService;
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
    public ProductOrder createProductOrder(Long clientId, ProductOrder productOrder) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + "not found"));

        productRepository.findById(productOrder.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " +
                        productOrder.getProduct().getId() + "not found"));

        productOrder.setClient(client);

        ProductOrder newProductOrder = productOrderRepository.save(productOrder);
        client.getProductOrder().add(newProductOrder);
        return newProductOrder;
    }

    @Override
    public ProductOrder updateProductOrder(Long id, ProductOrder productOrder) {
        return null;
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
