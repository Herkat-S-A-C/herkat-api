package com.herkat.services;

import com.herkat.dtos.inventory_balance.InventoryBalanceDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.InventoryBalance;
import com.herkat.models.Item;
import com.herkat.repositories.InventoryBalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InventoryBalanceService {

    private final InventoryBalanceRepository balanceRepository;

    public InventoryBalanceService(InventoryBalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public void createInitialBalance(Item item) {
        InventoryBalance balance = InventoryBalance.newInventoryBalance(
                item,
                BigDecimal.ZERO
        );
        balanceRepository.save(balance);
    }


    @Transactional(readOnly = true)
    public List<InventoryBalanceDto> findAll() {
        // Buscamos todos los balances
        return balanceRepository.findAll()
                .stream()
                .map(InventoryBalanceDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public InventoryBalanceDto findById(Integer id) {
        // Buscamos el balance por su ID
        return balanceRepository.findById(id)
                .map(InventoryBalanceDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.BALANCE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public InventoryBalanceDto findByItemId(Integer itemId) {
        // Buscamos el balance por el ID de su ítem
        return balanceRepository.findByItemId(itemId)
                .map(InventoryBalanceDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.BALANCE_NOT_FOUND));
    }

}
