package com.herkat.dtos.service_item_type;

import com.herkat.models.ServiceItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewServiceItemType {

    @NotBlank(message = "El nombre del tipo de servicio es obligatorio.")
    @Size(max = 50, message = "El nombre del tipo de servicio no puede superar los 50 caracteres.")
    private String name;

    public static ServiceItemType toEntity(NewServiceItemType newServiceItemType) {
        return ServiceItemType.newServiceItemType(newServiceItemType.getName());
    }

}
