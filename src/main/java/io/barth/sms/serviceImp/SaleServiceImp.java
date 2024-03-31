package io.barth.sms.serviceImp;

import io.barth.sms.entity.Sale;
import io.barth.sms.repository.SaleRepository;
import io.barth.sms.service.SaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImp implements SaleService {

    private final SaleRepository saleRepository;

    public SaleServiceImp(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Sale createSale(Sale sale) {
        sale.setCreatedDate(LocalDateTime.now());
        return saleRepository.save(sale);
    }

    @Override
    public Sale updateSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getSales() {
        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    @Override
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
