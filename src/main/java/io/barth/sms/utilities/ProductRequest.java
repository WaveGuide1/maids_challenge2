package io.barth.sms.utilities;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class ProductRequest {

    private String productName;

    private String description;

    private Integer quantity;

    private Integer price;

}
