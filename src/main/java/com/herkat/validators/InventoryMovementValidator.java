package com.herkat.validators;

import com.herkat.dtos.inventory_movement.NewInventoryMovementDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.InventoryBalance;
import com.herkat.models.Item;
import com.herkat.models.MovementType;
import com.herkat.repositories.InventoryBalanceRepository;
import com.herkat.repositories.ItemRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InventoryMovementValidator {

    private final ItemRepository itemRepository;
    private final InventoryBalanceRepository balanceRepository;

    public InventoryMovementValidator(ItemRepository itemRepository, InventoryBalanceRepository balanceRepository) {
        this.itemRepository = itemRepository;
        this.balanceRepository = balanceRepository;
    }

    public Item validateItemExists(Integer itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new HerkatException(ErrorMessage.ITEM_NOT_FOUND));
    }

    public void validateMovement(NewInventoryMovementDto dto) {
        InventoryBalance existingBalance = balanceRepository.findByItemId(dto.getItemId())
                .orElseThrow(() -> new HerkatException(ErrorMessage.BALANCE_NOT_FOUND));

        if (dto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new HerkatException(ErrorMessage.INVALID_QUANTITY);
        }

        if (dto.getType() == MovementType.OUT &&
                existingBalance.getCurrentQuantity().compareTo(dto.getQuantity()) < 0) {
            throw new HerkatException(ErrorMessage.INSUFFICIENT_STOCK);
        }
    }

}
