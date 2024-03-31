package io.barth.sms.serviceImp;

import io.barth.sms.entity.ProductOrder;
import io.barth.sms.repository.ProductOrderRepository;
import io.barth.sms.service.ProductOrderService;
import io.barth.sms.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderServiceImp implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImp(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public ProductOrder createProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public ProductOrder updateProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public List<ProductOrder> getProductOrder() {
        return productOrderRepository.findAll();
    }

    @Override
    public Optional<ProductOrder> getProductOrderById(Long id) {
        return productOrderRepository.findById(id);
    }

    @Override
    public void deleteProductOrder(Long id) {
        productOrderRepository.deleteById(id);
    }
}
