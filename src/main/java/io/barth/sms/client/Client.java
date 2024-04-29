package io.barth.sms.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.barth.sms.order.ProductOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "client")
@EntityListeners(AuditingEntityListener.class)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "You must provide first name")
    private String name;

    @Email(message = "Provide valid email address")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "clientOrder",
            joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "productOrderId")
    )
    @JsonIgnoreProperties({"confirm"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductOrder> productOrder;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

}
