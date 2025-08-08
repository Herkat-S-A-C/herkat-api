package com.herkat.dtos.machine;

import com.herkat.models.Image;
import com.herkat.models.Machine;
import com.herkat.models.MachineType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMachineDto {

    @Size(max = 50, message = "El nombre de la máquina no puede superar los 50 caracteres.")
    private String name;

    private Integer typeId;

    private String description;

    public static Machine updateEntity(UpdateMachineDto dto,
                                       Machine existingMachine,
                                       MachineType newType,
                                       Image newImage) {
        return new Machine(
                existingMachine.getId(),
                dto.getName() != null ? dto.getName() : existingMachine.getName(),
                newType != null ? newType : existingMachine.getType(),
                dto.getDescription() != null ? dto.getDescription() : existingMachine.getDescription(),
                newImage != null ? newImage : existingMachine.getImage(),
                existingMachine.getRegistrationDate()
        );
    }

}
