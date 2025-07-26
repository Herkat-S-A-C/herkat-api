package com.herkat.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductTypeRequestDTO {

    @NotBlank(message = "El nombre del tipo de producto es obligatorio.")
    @Size(max = 50)
    private String name;

}
