package com.herkat.validators;

import com.herkat.dtos.inventory.NewInventoryDto;
import com.herkat.dtos.inventory.UpdateInventoryDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.InventoryRepository;
import org.springframework.stereotype.Component;

@Component
public class InventoryValidator {

    private final InventoryRepository repository;
    public InventoryValidator(InventoryRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewInventoryDto dto){
        if (dto.getStock() == null) {
            throw new BadRequestException("El stock no puede estar vacío.");
        }

        if (repository.findByProductId(dto.getStock()).isPresent()) {
            throw new ConflictException("Ya existe un inventario registrado para el stock.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateInventoryDto dto){
        repository.findByProductId(dto.getStock())
                .ifPresent(existingInventory -> {
                    if (!existingInventory.getId().equals(id)) {
                        throw new ConflictException("Ya existe un inventario registrado para el stock.");
                    }
                });
    }
}
