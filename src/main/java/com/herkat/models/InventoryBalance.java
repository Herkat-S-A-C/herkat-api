package com.herkat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory_balances")
public class InventoryBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Item item;

    @Column(nullable = false)
    private BigDecimal currentQuantity = BigDecimal.ZERO;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    public static InventoryBalance newInventoryBalance(Item item, BigDecimal currentQuantity) {
        return new InventoryBalance(
                null,
                item,
                currentQuantity,
                null
        );
    }

}
