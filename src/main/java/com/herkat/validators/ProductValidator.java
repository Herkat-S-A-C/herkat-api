package com.herkat.validators;

import com.herkat.dtos.product.ProductRequestDTO;
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

    public void validateBeforeRegister(ProductRequestDTO dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del producto no puede estar vacío.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del producto ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, ProductRequestDTO dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del producto no puede estar vacío.");
        }

        repository.findByNameIgnoreCase(dto.getName()).ifPresent(existingProduct -> {
            if(!existingProduct.getId().equals(id)) {
                throw new ConflictException("El nombre del producto ya existe.");
            }
        });
    }

}
