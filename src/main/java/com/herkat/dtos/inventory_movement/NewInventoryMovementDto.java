package com.herkat.dtos.inventory_movement;

import com.herkat.models.InventoryMovement;
import com.herkat.models.Item;
import com.herkat.models.MovementType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewInventoryMovementDto {

    @NotNull(message = "El ID del ítem es obligatorio.")
    private Integer itemId;

    @NotNull(message = "El tipo de movimiento es obligatorio.")
    private MovementType type;

    @NotNull(message = "La cantidad es obligatoria.")
    @DecimalMin(value = "0.0", message = "La cantidad no puede ser negativa.")
    private BigDecimal quantity;

    public static InventoryMovement toEntity(NewInventoryMovementDto dto, Item item){
        return InventoryMovement.newInventoryMovement(
                item,
                dto.getType(),
                dto.getQuantity()
        );
    }

}
