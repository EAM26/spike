package com.em26.Spike.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serialNumber;
    private String storageLocation;
    private LocalDateTime receiptDateTime;
    private BigDecimal totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private User user;




}
