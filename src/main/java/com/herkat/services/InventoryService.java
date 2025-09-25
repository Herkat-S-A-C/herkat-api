package com.herkat.services;

import com.herkat.dtos.inventory.InventoryDto;
import com.herkat.dtos.inventory.NewInventoryDto;
import com.herkat.dtos.inventory.UpdateInventoryDto;
import com.herkat.models.Inventory;
import com.herkat.repositories.InventoryRepository;
import com.herkat.validators.InventoryValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryValidator validator;

    public InventoryService(InventoryRepository inventoryRepository,
                            InventoryValidator validator){
        this.inventoryRepository = inventoryRepository;
        this.validator = validator;
    }

    @Transactional
    public InventoryDto register(NewInventoryDto newInventoryDto){
        //Validar con InventoryValidator antes de registrar
        validator.validateBeforeRegister(
                newInventoryDto
        );

        //Convertimos el Dto a entidad
        Inventory newInventory = newInventoryDto.toEntity(
                newInventoryDto
        );

        //Guardar el Dto en el repository
        Inventory savedInventory = inventoryRepository.save(newInventory);

        return InventoryDto.fromEntity(savedInventory);
    }

    @Transactional(readOnly = true)
    public List<InventoryDto> findAll(){
        //Retornar todos los inventarios registrados
        return inventoryRepository.findAll()
                .stream()
                .map(InventoryDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public InventoryDto findById(Integer id){
        //Buscamos al stock por su Id
        return inventoryRepository.findById(id)
                .map(InventoryDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Stocj con ID: " + id + " no encontrado."));
    }

    @Transactional
    public InventoryDto update(Integer id, UpdateInventoryDto updateInventoryDto){
        //buscamos al stock por su Id
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Stock con Id: "+ id +" no encontrado."));

        //creamos entidad con los datos actualizados
        Inventory updateInventory = UpdateInventoryDto.updateEntity(
                updateInventoryDto,
                existingInventory
        );

        //guardamos los cambios de la entidad en el repository
        Inventory savedInventory = inventoryRepository.save(updateInventory);

        return InventoryDto.fromEntity(savedInventory);
    }

    @Transactional
    //Eliminar el registro de stock por su Id
    public void delete(Integer id){
        //buscar stock por su Id
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Stock con Id: "+ id +" no encontrado."));

        //eliminamos el stock del inventario
        inventoryRepository.delete(existingInventory);
    }
}
