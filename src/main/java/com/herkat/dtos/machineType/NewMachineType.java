package com.herkat.dtos.machineType;

import com.herkat.models.MachineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewMachineType {

    @NotBlank(message = "El nombre del tipo de máquina no puede estar vacío.")
    @Size(max = 50, message = "El nombre del tipo de máquina no puede superar los 50 caracteres.")
    private String name;

    public static MachineType toEntity(NewMachineType newMachineType) {
        return MachineType.newMachineType(newMachineType.getName());
    }

}
