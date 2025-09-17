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
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)   //ID autogenerado
    private Integer id;

    @Column(nullable = false)   //los datos ingresados no pueden ser nulos
    private Integer stock;

    private LocalDateTime updateAt;   //fecha y hora de ultima actualizacion

    @PrePersist
    protected void prePersist(){                 //actualizar día
        this.updateAt = LocalDateTime.now();
    }

    public static Inventory newInventory(Integer stock, LocalDateTime updateAt){      //inicializar nuevo registro de inventario
        return new Inventory(null, stock, updateAt);
    }
}
