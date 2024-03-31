package io.barth.sms.controllers;

import io.barth.sms.entity.Sale;
import io.barth.sms.serviceImp.SaleServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/sales")
public class SaleController {

    private final SaleServiceImp saleServiceImp;

    public SaleController(SaleServiceImp saleServiceImp) {
        this.saleServiceImp = saleServiceImp;
    }

    @GetMapping("/")
    public List<Sale> getAllSales() {
        return saleServiceImp.getSales();
    }

    @PostMapping("/")
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        Sale createdSales = saleServiceImp.createSale(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSales);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody Sale sale) {
        Optional<Sale> oldSale = saleServiceImp.getSaleById(id);
        if (oldSale.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sale updatedSale = oldSale.get();
        updatedSale.setClient(sale.getClient());
        updatedSale.setUser(sale.getUser());

        updatedSale = saleServiceImp.updateSale(updatedSale);
        return ResponseEntity.ok(updatedSale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Optional<Sale> salesOrder = saleServiceImp.getSaleById(id);
        return salesOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleServiceImp.deleteSale(id);
        return ResponseEntity.noContent().build(); }
}
