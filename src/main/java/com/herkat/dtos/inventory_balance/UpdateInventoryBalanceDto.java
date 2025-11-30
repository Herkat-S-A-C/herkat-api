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
public class UpdateInventoryBalanceDto {

    @NotNull(message = "La nueva cantidad es obligatoria.")
    @DecimalMin(value = "0.0", message = "La cantidad no puede ser negativa.")
    private BigDecimal currentQuantity;

    public static InventoryBalance updateEntity(UpdateInventoryBalanceDto dto,
                                                InventoryBalance existingBalance,
                                                Item newItem){
        return new InventoryBalance(
                existingBalance.getId(),
                newItem != null ? newItem : existingBalance.getItem(),
                dto.getCurrentQuantity(),
                existingBalance.getLastUpdated()
        );
    }

}
