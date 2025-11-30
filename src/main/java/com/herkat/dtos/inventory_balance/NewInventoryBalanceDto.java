package com.herkat.dtos.inventory_balance;

import com.herkat.models.InventoryBalance;
import com.herkat.models.Item;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewInventoryBalanceDto {

    @NotNull(message = "El ID del ítem es obligatorio.")
    private Integer itemId;

    @NotNull(message = "La cantidad inicial es obligatoria.")
    @DecimalMin(value = "0.0", message = "La cantidad no puede ser negativa.")
    private BigDecimal currentQuantity;

    public static InventoryBalance toEntity(NewInventoryBalanceDto newInventoryBalanceDto, Item item){
        return InventoryBalance.newInventoryBalance(
                item,
                newInventoryBalanceDto.getCurrentQuantity()
        );
    }

}
