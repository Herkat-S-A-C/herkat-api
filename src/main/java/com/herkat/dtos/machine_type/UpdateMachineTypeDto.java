package com.herkat.dtos.machine_type;

import com.herkat.models.MachineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMachineTypeDto {

    @NotBlank(message = "El nombre del tipo de máquina es obligatorio.")
    @Size(max = 50, message = "El nombre del tipo de máquina no puede estar vacío.")
    private String name;

    public static MachineType updateEntity(UpdateMachineTypeDto dto, MachineType existingType) {
        return new MachineType(
                existingType.getId(),
                dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : existingType.getName()
        );
    }

}
