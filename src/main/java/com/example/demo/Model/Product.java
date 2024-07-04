package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private double price;
    private String description;
    @Column(nullable = true)
    private String imageProduct;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}