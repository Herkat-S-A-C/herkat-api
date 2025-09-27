package com.herkat.dtos.machine_type;

import com.herkat.models.MachineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewMachineTypeDto {

    @NotBlank(message = "El nombre del tipo de máquina es obligatorio.")
    @Size(max = 50, message = "El nombre del tipo de máquina no puede superar los 50 caracteres.")
    private String name;

    public static MachineType toEntity(NewMachineTypeDto newMachineTypeDto) {
        return MachineType.newMachineType(newMachineTypeDto.getName());
    }

}
