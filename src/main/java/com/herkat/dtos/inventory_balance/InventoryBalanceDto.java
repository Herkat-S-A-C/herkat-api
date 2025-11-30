package com.herkat.dtos.inventory_balance;

import com.herkat.models.InventoryBalance;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InventoryBalanceDto {

    private Integer id;

    private Integer itemId;

    private String itemName;

    private String itemType;

    private BigDecimal currentQuantity;

    private LocalDateTime lastUpdated;

    public static InventoryBalanceDto fromEntity(InventoryBalance balance){
        return new InventoryBalanceDto(
                balance.getId(),
                balance.getItem().getId(),
                balance.getItem().getName(),
                balance.getItem().getType().name(),
                balance.getCurrentQuantity(),
                balance.getLastUpdated()
        );
    }

}
