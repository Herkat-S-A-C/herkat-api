package com.herkat.dtos.machineType;

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

    @NotBlank(message = "El nombre del tipo de máquina no puede estar vacío.")
    @Size(max = 50, message = "El nombre del tipo de máquina no puede estar vacío.")
    private String name;

    public static MachineType updateEntity(UpdateMachineTypeDto updateMachineTypeDto,
                                                MachineType existingType) {
        return new MachineType(
                existingType.getId(),
                updateMachineTypeDto.getName() != null ? updateMachineTypeDto.getName() : existingType.getName()
        );
    }

}
