package com.herkat.dtos.product;

import com.herkat.models.Image;
import com.herkat.models.Product;
import com.herkat.models.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDto {

    @NotBlank(message = "El nombre del producto el obligatorio.")
    @Size(max = 50, message = "El producto no puede superar los 50 caracteres.")
    private String name;

    @NotNull(message = "El tipo de producto es obligatorio.")
    private Integer typeId;

    @NotNull(message = "La capacidad del producto es obligatoria.")
    private Double capacity;

    @NotBlank(message = "La descripción del producto es obligatoria.")
    private String description;

    public static Product toEntity(NewProductDto newProductDto, ProductType type, Image image) {
        return Product.newProduct(
                newProductDto.getName(),
                type,
                newProductDto.getCapacity(),
                newProductDto.getDescription(),
                image,
                null
        );
    }

}
