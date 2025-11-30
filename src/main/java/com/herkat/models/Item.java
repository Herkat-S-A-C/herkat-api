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
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ID del Product o Machine al que pertenece
    @Column(name = "reference_id", nullable = false)
    private Integer referenceId;

    // PRODUCT o MACHINE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ItemType type;

    // Copia ligera del nombre para no depender de joins
    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static Item newItem(Integer referenceId, String name, ItemType type) {
        return new Item(
                null,
                referenceId,
                type,
                name,
                null,
                null
        );
    }

}

