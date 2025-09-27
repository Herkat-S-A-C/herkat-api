package com.herkat.services;

import com.herkat.dtos.product_type.NewProductTypeDto;
import com.herkat.dtos.product_type.ProductTypeDto;
import com.herkat.dtos.product_type.UpdateProductTypeDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.ProductType;
import com.herkat.repositories.ProductTypeRepository;
import com.herkat.validators.ProductTypeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ProductTypeDto register(NewProductTypeDto newProductTypeDto) {
        // Validamos las reglas de negocio antes de registrar
        validator.validateNameUniqueness(newProductTypeDto.getName());

        // Convertimos el DTO a entidad
        ProductType newProductType = NewProductTypeDto.toEntity(newProductTypeDto);

        // Guardamos el nuevo tipo en la base de datos
        ProductType savedProductType = repository.save(newProductType);

        // Convertimos la entidad guardada a DTO para retornarlo
        return ProductTypeDto.fromEntity(savedProductType);
    }

    @Transactional(readOnly = true)
    public List<ProductTypeDto> findAll() {
        // Buscamos todos los tipos de producto
        return repository.findAll()
                .stream()
                .map(ProductTypeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductTypeDto findById(Integer id) {
        // Buscamos tipo de producto por su ID
         return repository.findById(id)
                 .map(ProductTypeDto::fromEntity)
                 .orElseThrow(() -> new HerkatException(ErrorMessage.PRODUCT_TYPE_NOT_FOUND));
    }

    public ProductTypeDto findByName(String name) {
        // Buscamos tipo de producto por su nombre
        return repository.findByNameIgnoreCase(name)
                .map(ProductTypeDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.PRODUCT_TYPE_NOT_FOUND));
    }

    @Transactional
    public ProductTypeDto update(Integer id, UpdateProductTypeDto updateProductTypeDto) {
        // Validamos las reglas de negocio antes de actualizar
        validator.validateNameOnUpdate(id, updateProductTypeDto.getName());

        // Buscamos el tipo de producto por su ID
        ProductType existingType = repository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.PRODUCT_TYPE_NOT_FOUND));

        // Creamos la nueva instancia con los campos actualizados
        ProductType updatedType = UpdateProductTypeDto.updateEntity(updateProductTypeDto, existingType);

        // Guardamos los cambios en la base de datos
        ProductType savedType = repository.save(updatedType);

        // Convertimos la entidad a DTO para retornarlo
        return ProductTypeDto.fromEntity(savedType);
    }

    @Transactional
    public void delete(Integer id) {
        // Buscamos el tipo de producto por su ID
        ProductType existingType = repository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.PRODUCT_TYPE_NOT_FOUND));

        // Eliminamos el tipo de producto de la base de datos
        repository.delete(existingType);
    }

}
