package com.herkat.dtos.inventory_movement;

import com.herkat.models.InventoryMovement;
import com.herkat.models.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InventoryMovementDto {

    private Integer id;

    private Integer itemId;

    private String itemName;

    private String itemType;

    private MovementType type;

    private BigDecimal quantity;

    private LocalDateTime createdAt;

    public static InventoryMovementDto fromEntity(InventoryMovement movement){
        return new InventoryMovementDto(
                movement.getId(),
                movement.getItem().getId(),
                movement.getItem().getName(),
                movement.getItem().getType().name(),
                movement.getType(),
                movement.getQuantity(),
                movement.getCreatedAt()
        );
    }

}