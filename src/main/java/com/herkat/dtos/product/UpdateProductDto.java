package com.herkat.dtos.product;

import com.herkat.models.Image;
import com.herkat.models.Product;
import com.herkat.models.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {

    @Size(max = 50, message = "El nombre del producto no puede superar los 50 caracteres.")
    private String name;

    private Integer typeId;

    private Double capacity;

    private String description;

    private Boolean isFeatured;

    public static Product updateEntity(UpdateProductDto dto,
                                       Product existingProduct,
                                       ProductType newType,
                                       Image newImage) {
        return new Product(
                existingProduct.getId(),
                dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : existingProduct.getName(),
                newType != null ? newType : existingProduct.getType(),
                dto.getCapacity() != null ? dto.getCapacity() : existingProduct.getCapacity(),
                dto.getDescription() != null && !dto.getDescription().isEmpty() ? dto.getDescription() : existingProduct.getDescription(),
                newImage != null ? newImage : existingProduct.getImage(),
                dto.getIsFeatured() != null ? dto.getIsFeatured() : existingProduct.getIsFeatured(),
                existingProduct.getCreatedAt()
        );
    }

}
