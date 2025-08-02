package com.herkat.dtos.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDTO {

    private Integer id;

    private String name;

    private String typeName;

    private Integer capacity;

    private String description;

    private String imageUrl;

    private String imagePublicId;

    private LocalDateTime registrationDate;



}
