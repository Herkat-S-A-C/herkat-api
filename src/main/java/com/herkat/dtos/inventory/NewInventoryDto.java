package com.herkat.dtos.inventory;

import com.herkat.models.Inventory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewInventoryDto {

    @NotNull(message = "El ingreso del stock es obligatorio")
    private Integer stock;

    public static Inventory toEntity(NewInventoryDto newInventoryDto){
        return Inventory.newInventory(
                newInventoryDto.getStock()
        );
    }
}
