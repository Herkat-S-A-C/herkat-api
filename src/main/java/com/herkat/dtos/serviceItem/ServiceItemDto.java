package com.herkat.dtos.serviceItem;

import com.herkat.models.ServiceItem;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceItemDto {

    private Integer id;

    private String name;

    private String typeName;

    private String description;

    private String imageUrl;

    private Boolean isFeatured;

    public static ServiceItemDto fromEntity(ServiceItem serviceItem) {
        return new ServiceItemDto(
                serviceItem.getId(),
                serviceItem.getName(),
                serviceItem.getType().getName(),
                serviceItem.getDescription(),
                serviceItem.getImage().getUrl(),
                serviceItem.getIsFeatured()
        );
    }

}
