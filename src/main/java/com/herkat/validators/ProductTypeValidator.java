package com.herkat.validators;

import com.herkat.dtos.ProductTypeRequestDTO;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.ProductTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeValidator {

    private final ProductTypeRepository repository;

    public ProductTypeValidator(ProductTypeRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(ProductTypeRequestDTO dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del tipo de producto no puede estar vacío.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del tipo de producto ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, ProductTypeRequestDTO dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del tipo de producto no puede estar vacío.");
        }

        repository.findByNameIgnoreCase(dto.getName()).ifPresent(existingType -> {
            if(!existingType.getId().equals(id)) {
                throw new ConflictException("El nombre del tipo de producto ya existe.");
            }
        });
    }

}
