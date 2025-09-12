package com.herkat.dtos.service_item;

import com.herkat.models.Image;
import com.herkat.models.ServiceItem;
import com.herkat.models.ServiceItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewServiceItemDto {

    @NotBlank(message = "El nombre del servicio es obligatorio.")
    @Size(max = 50, message = "El nombre del servicio no puede superar los 50 caracteres.")
    private String name;

    @NotNull(message = "El tipo de servicio es obligatorio.")
    private Integer typeId;

    @NotBlank(message = "La descripción del servicio es obligatoria.")
    private String description;

    @NotNull(message = "Debe indicar si el servicio es destacado o no.")
    private Boolean isFeatured;

    public static ServiceItem toEntity(NewServiceItemDto newServiceItemDto, ServiceItemType type, Image image) {
        return ServiceItem.newServiceItem(
                newServiceItemDto.getName(),
                type,
                newServiceItemDto.getDescription(),
                image,
                newServiceItemDto.getIsFeatured(),
                null
        );
    }

}
