package com.herkat.dtos.serviceItem;

import com.herkat.models.Image;
import com.herkat.models.ServiceItem;
import com.herkat.models.ServiceItemType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateServiceItemDto {

    @Size(max = 50, message = "El nombre del servicio no puede superar los 50 caracteres.")
    private String name;

    private Integer typeId;

    private String description;

    public static ServiceItem updateEntity(UpdateServiceItemDto dto,
                                           ServiceItem existingServiceItem,
                                           ServiceItemType newType,
                                           Image newImage) {
        return new ServiceItem(
                existingServiceItem.getId(),
                dto.getName() != null ? dto.getName() : existingServiceItem.getName(),
                newType != null ? newType : existingServiceItem.getType(),
                dto.getDescription() != null ? dto.getDescription() : existingServiceItem.getDescription(),
                newImage != null ? newImage : existingServiceItem.getImage(),
                existingServiceItem.getRegistrationDate()
        );
    }

}
