package com.em26.Spike.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ToString.Exclude
    private String apiKey;
    private String city;

    @ToString.Exclude
    @OneToMany(mappedBy = "shop")
    private List<Receipt> receipts = new ArrayList<>();

}


