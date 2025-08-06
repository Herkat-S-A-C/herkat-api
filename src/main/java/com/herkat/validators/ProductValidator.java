package com.herkat.validators;

import com.herkat.dtos.product.NewProductDto;
import com.herkat.dtos.product.UpdateProductDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    private final ProductRepository repository;

    public ProductValidator(ProductRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewProductDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del producto no puede estar vacío.");
        }

        if(dto.getTypeId() == null) {
            throw new BadRequestException("El ID del tipo de producto no puede estar vacío.");
        }

        if(dto.getCapacity() == null) {
            throw new BadRequestException("La capacidad del producto no puede estar vacía.");
        }

        if(dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new BadRequestException("La descripción del producto no puede estar vacía.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del producto ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateProductDto dto) {
        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(existingProduct -> {
                    if(!existingProduct.getId().equals(id)) {
                        throw new ConflictException("El nombre del producto ya existe.");
                    }
                });
    }

}
