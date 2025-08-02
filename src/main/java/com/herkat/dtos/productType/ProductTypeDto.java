package com.herkat.dtos.productType;

import com.herkat.models.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductTypeDto {

    private Integer id;

    private String name;

    public static ProductTypeDto fromEntity(ProductType productType) {
        return new ProductTypeDto(
                productType.getId(),
                productType.getName());
    }

}
