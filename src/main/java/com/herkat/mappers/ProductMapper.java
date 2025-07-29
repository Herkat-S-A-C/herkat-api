package com.herkat.mappers;

import com.herkat.dtos.ProductRequestDTO;
import com.herkat.dtos.ProductResponseDTO;
import com.herkat.models.Product;
import com.herkat.models.ProductType;
import com.herkat.repositories.ProductTypeRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ProductMapper {

    private final ProductTypeRepository typeRepository;

    public ProductMapper(ProductTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public ProductResponseDTO toDTO(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setTypeName(product.getType().getName());
        dto.setCapacity(product.getCapacity());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setImagePublicId(product.getImagePublicId());
        dto.setRegistrationDate(product.getRegistrationDate());
        return dto;

    }

    public Product toEntity(ProductRequestDTO dto) {
        ProductType type = typeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setType(type);
        product.setCapacity(dto.getCapacity());
        product.setDescription(dto.getDescription());

        return product;

    }

}
