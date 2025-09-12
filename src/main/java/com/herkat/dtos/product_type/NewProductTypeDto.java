package com.herkat.dtos.product_type;

import com.herkat.models.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewProductTypeDto {

    @NotBlank(message = "El nombre del tipo de producto es obligatorio.")
    @Size(max = 50, message = "El nombre del tipo de producto no puede superar los 50 caracteres.")
    private String name;

    public static ProductType toEntity(NewProductTypeDto newProductTypeDto) {
        return ProductType.newProductType(newProductTypeDto.getName());
    }

}
