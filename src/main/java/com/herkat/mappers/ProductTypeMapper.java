package com.herkat.mappers;

import com.herkat.dtos.ProductTypeRequestDTO;
import com.herkat.dtos.ProductTypeResponseDTO;
import com.herkat.models.ProductType;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeMapper {

    public ProductTypeResponseDTO toDTO(ProductType productType) {

        ProductTypeResponseDTO dto = new ProductTypeResponseDTO();
        dto.setId(productType.getId());
        dto.setName(productType.getName());
        return dto;

    }

    public ProductType toEntity(ProductTypeRequestDTO dto) {

        ProductType type = new ProductType();
        type.setName(dto.getName());
        return type;

    }

}
