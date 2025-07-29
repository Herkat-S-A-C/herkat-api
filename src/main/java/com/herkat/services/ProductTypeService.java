package com.herkat.services;

import com.herkat.dtos.ProductTypeRequestDTO;
import com.herkat.dtos.ProductTypeResponseDTO;
import com.herkat.mappers.ProductTypeMapper;
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
    private final ProductTypeMapper mapper;

    public ProductTypeService(ProductTypeMapper mapper,
                              ProductTypeValidator validator,
                              ProductTypeRepository repository) {
        this.mapper = mapper;
        this.validator = validator;
        this.repository = repository;
    }

    public ProductTypeResponseDTO register(ProductTypeRequestDTO requestDTO) {
        // Validamos las reglas de negocio antes de registrar
        validator.validateBeforeRegister(requestDTO);

        // Convertimos el DTO a entidad
        ProductType newProductType = mapper.toEntity(requestDTO);
        newProductType.setId(null); // Forzamos que Hibernate lo trate como un nuevo registro

        // Guardamos el nuevo tipo en la base de datos
        ProductType savedProductType = repository.save(newProductType);

        // Convertimos la entidad guardada a DTO para retornarlo
        return mapper.toDTO(savedProductType);
    }

    public List<ProductTypeResponseDTO> findAll() {
        // Buscamos todos los tipos de producto
        List<ProductType> productTypes = repository.findAll();

        // Validamos que la lista no esté vacía
        if(productTypes.isEmpty()) {
            throw new NoSuchElementException("No hay tipos de productos registrados.");
        }

        // Convertimos la lista de entidades a DTO para retornarlo
        return productTypes.stream().map(mapper::toDTO).toList();
    }

    public ProductTypeResponseDTO findById(Integer id) {
        // Buscar tipo de producto por su ID
        ProductType productType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado."));

        // Convertimos entidad a DTO para retornarlo
        return mapper.toDTO(productType);
    }

    public ProductTypeResponseDTO findByName(String name) {
        // Buscar tipo de producto por su nombre
        ProductType productType = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado"));

        // Convertimos la entidad a DTO para retornarlo
        return mapper.toDTO(productType);
    }

    public ProductTypeResponseDTO update(Integer id, ProductTypeRequestDTO requestDTO) {
        // Validamos los datos antes de actualizar
        validator.validateBeforeUpdate(id, requestDTO);

        // Buscamos el tipo de producto por su ID
        ProductType existingType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado"));

        // Seteamos el nuevo nombre
        existingType.setName(requestDTO.getName());

        // Guardamos los cambios en la base de datos
        ProductType updatedType = repository.save(existingType);

        // Convertimos la entidad a DTO para retornarlo
        return mapper.toDTO(updatedType);
    }

    public void delete(Integer id) {
        // Buscamos el tipo de producto por su ID
        ProductType existingType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado"));

        // Eliminamos el tipo de producto de la base de datos
        repository.delete(existingType);
    }

}
