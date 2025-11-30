package com.herkat.dtos.inventory_movement;

import com.herkat.models.InventoryMovement;
import com.herkat.models.Item;
import com.herkat.models.MovementType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UpdateInventoryMovementDto {

    @NotNull(message = "El tipo de movimiento es obligatorio.")
    private MovementType type;

    @NotNull(message = "La cantidad es obligatoria.")
    @DecimalMin(value = "0.0", message = "La cantidad no puede ser negativa.")
    private BigDecimal quantity;

    public static InventoryMovement updateEntity(UpdateInventoryMovementDto dto,
                                                 InventoryMovement existingMovement,
                                                 Item newItem){
        return new InventoryMovement(
                existingMovement.getId(),
                newItem != null ? newItem : existingMovement.getItem(),
                dto.getType(),
                dto.getQuantity(),
                existingMovement.getCreatedAt()
        );
    }

}
