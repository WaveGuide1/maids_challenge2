package io.barth.sms.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.barth.sms.client.Client;
import io.barth.sms.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@Table(name = "product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "productOrder")
    @JsonIgnore
    @JsonIgnoreProperties({"address", "createdDate", "lastModified"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Client> client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"quantity", "createdDate", "lastModified"})
    private Product product;

    @NotNull(message = "You must provide number of order")
    private Integer quantity = 1;

    private Boolean confirm = false;
}
