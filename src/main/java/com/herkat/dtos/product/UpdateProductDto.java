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

    @Size(max = 50, message = "El nombre del productno no puede superar los 50 caracteres.")
    private String name;

    private Integer typeId;

    private Integer capacity;

    private String description;

    private String imageUrl;

    public static Product updateEntity(UpdateProductDto dto,
                                       Product existingProduct,
                                       ProductType newType,
                                       Image newImage) {
        return new Product(
                existingProduct.getId(),
                dto.getName() != null ? dto.getName() : existingProduct.getName(),
                newType != null ? newType : existingProduct.getType(),
                dto.getCapacity() != null ? dto.getCapacity() : existingProduct.getCapacity(),
                dto.getDescription() != null ? dto.getDescription() : existingProduct.getDescription(),
                newImage != null ? newImage : existingProduct.getImage(),
                existingProduct.getRegistrationDate()
        );
    }

}
