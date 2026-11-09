package com.em26.Spike.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String apiKey;
    private String city;

    @OneToMany(mappedBy = "shop")
    private List<Receipt> receipts;

}


