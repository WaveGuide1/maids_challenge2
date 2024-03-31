package io.barth.sms.service;

import io.barth.sms.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    public Sale createSale(Sale sale);

    public Sale updateSale(Sale sale);

    public List<Sale> getSales();

    public Optional<Sale> getSaleById(Long id);

    public void deleteSale(Long id);
}
