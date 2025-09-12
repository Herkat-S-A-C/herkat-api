package com.herkat.dtos.product_type;

import com.herkat.models.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductTypeDto {

    @NotBlank(message = "El nombre del tipo de producto es obligatorio.")
    @Size(max = 50, message = "El nombre del tipo de producto no puede superar los 50 carácteres.")
    private String name;

    public static ProductType updateEntity(UpdateProductTypeDto dto,
                                           ProductType productType) {
        return new ProductType(
                productType.getId(),
                dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : productType.getName()
        );
    }

}
