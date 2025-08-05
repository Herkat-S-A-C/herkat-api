package com.herkat.dtos.product;

import com.herkat.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductDto {

    private Integer id;

    private String name;

    private String typeName;

    private Integer capacity;

    private String description;

    private String imageUrl;

    public static ProductDto fromEntity(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getType().getName(),
                product.getCapacity(),
                product.getDescription(),
                product.getImage().getUrl()
        );
    }

}
