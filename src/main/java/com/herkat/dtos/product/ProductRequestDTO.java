package com.herkat.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = "El nombre del producto el obligatorio.")
    @Size(max = 150, message = "El producto no puede superar los 150 carácteres.")
    private String name;

    @NotNull(message = "El tipo de producto es obligatorio.")
    private Integer typeId;

    @NotNull(message = "La capacidad del producto es obligatoria.")
    private Integer capacity;

    @NotBlank(message = "La descripción del producto es obligatoria.")
    private String description;

}
