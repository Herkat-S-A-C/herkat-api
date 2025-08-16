package com.herkat.dtos.machine;

import com.herkat.models.Image;
import com.herkat.models.Machine;
import com.herkat.models.MachineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMachineDto {

    @NotBlank(message = "El nombre de la máquina es obligatorio.")
    @Size(max = 50, message = "El nombre de la máquina no puede superar los 50 caracteres.")
    private String name;

    @NotNull(message = "El tipo de máquina es obligatorio.")
    private Integer typeId;

    @NotBlank(message = "La descripción de la máquina es obligatoria.")
    private String description;

    @NotNull(message = "Debe indicar si la máquina es destacado o no.")
    private Boolean isFeatured;

    public static Machine toEntity(NewMachineDto newMachineDto, MachineType type, Image image) {
        return Machine.newMachine(
                newMachineDto.getName(),
                type,
                newMachineDto.getDescription(),
                image,
                newMachineDto.getIsFeatured(),
                null
        );
    }

}
