package com.herkat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ProductType type;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String imagePublicId;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    public static Product newProduct(String name, ProductType type, Integer capacity, String description,
                                     String imageUrl, String imagePublicId, LocalDateTime registrationDate) {
        return new Product(
                null,
                name,
                type,
                capacity,
                description,
                imageUrl,
                imagePublicId,
                registrationDate
        );
    }

}
