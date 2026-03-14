package com.siva.rest.MVC.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "PRODUCT_SEQ",
            allocationSize = 1
    )
    private Long id;

    private String name;
    private double price;
    private String brand;
    private int quantity;
    private String imageName;
}