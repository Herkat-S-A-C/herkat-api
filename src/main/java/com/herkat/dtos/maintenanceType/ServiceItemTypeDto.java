package com.herkat.dtos.maintenanceType;

import com.herkat.models.ServiceItemType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceItemTypeDto {

    private Integer id;

    private String name;

    public static ServiceItemTypeDto fromEntity(ServiceItemType serviceItemType) {
        return new ServiceItemTypeDto(
                serviceItemType.getId(),
                serviceItemType.getName()
        );
    }

}
