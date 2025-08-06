package com.herkat.services;

import com.herkat.dtos.productType.NewProductTypeDto;
import com.herkat.dtos.productType.ProductTypeDto;
import com.herkat.dtos.productType.UpdateProductTypeDto;
import com.herkat.models.ProductType;
import com.herkat.repositories.ProductTypeRepository;
import com.herkat.validators.ProductTypeValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductTypeService {

    private final ProductTypeRepository repository;
    private final ProductTypeValidator validator;

    public ProductTypeService(ProductTypeRepository repository, ProductTypeValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public ProductTypeDto register(NewProductTypeDto requestDTO) {
        // Validamos las reglas de negocio antes de registrar
        validator.validateBeforeRegister(requestDTO);

        // Convertimos el DTO a entidad
        ProductType newProductType = NewProductTypeDto.toEntity(requestDTO);

        // Guardamos el nuevo tipo en la base de datos
        ProductType savedProductType = repository.save(newProductType);

        // Convertimos la entidad guardada a DTO para retornarlo
        return ProductTypeDto.fromEntity(savedProductType);
    }

    public List<ProductTypeDto> findAll() {
        // Buscamos todos los tipos de producto
        return repository.findAll()
                .stream()
                .map(ProductTypeDto::fromEntity)
                .toList();
    }

    public ProductTypeDto findById(Integer id) {
        // Buscar tipo de producto por su ID
         return repository.findById(id)
                 .map(ProductTypeDto::fromEntity)
                 .orElseThrow(() -> new NoSuchElementException("Tipo de producto con ID: " + id + " no encontrado."));
    }

    public ProductTypeDto findByName(String name) {
        // Buscar tipo de producto por su nombre
        return repository.findByNameIgnoreCase(name)
                .map(ProductTypeDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto con nombre: " + name + " no encontrado."));
    }

    public ProductTypeDto update(Integer id, UpdateProductTypeDto updateProductTypeDto) {
        // Validamos los datos antes de actualizar
        validator.validateBeforeUpdate(id, updateProductTypeDto);

        // Buscamos el tipo de producto por su ID
        ProductType existingType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto con ID: " + id + " no encontrado."));

        // Creamos la nueva instancia con los campos actualizados
        ProductType updatedType = UpdateProductTypeDto.updateEntity(updateProductTypeDto, existingType);

        // Guardamos los cambios en la base de datos
        ProductType savedType = repository.save(updatedType);

        // Convertimos la entidad a DTO para retornarlo
        return ProductTypeDto.fromEntity(savedType);
    }

    public void delete(Integer id) {
        // Buscamos el tipo de producto por su ID
        ProductType existingType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado"));

        // Eliminamos el tipo de producto de la base de datos
        repository.delete(existingType);
    }

}
