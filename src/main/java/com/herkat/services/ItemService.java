package com.herkat.services;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.*;
import com.herkat.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final InventoryBalanceService balanceService;

    public Item createItemForProduct(Product product) {
        Item item = Item.newItem(product.getId(), product.getName(), ItemType.PRODUCT);
        Item savedItem = itemRepository.save(item);

        balanceService.createInitialBalance(savedItem);

        return item;
    }

    public Item createItemForMachine(Machine machine) {
        Item item = Item.newItem(machine.getId(), machine.getName(), ItemType.MACHINE);
        Item savedItem = itemRepository.save(item);

        balanceService.createInitialBalance(savedItem);

        return item;
    }

    public Item findById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.ITEM_NOT_FOUND));
    }

    public Item findByReference(Integer referenceId, ItemType type) {
        return itemRepository.findByReferenceIdAndType(referenceId, type)
                .orElseThrow(() -> new HerkatException(ErrorMessage.ITEM_NOT_FOUND));
    }

}

