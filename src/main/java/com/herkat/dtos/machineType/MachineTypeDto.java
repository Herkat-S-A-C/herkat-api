package com.herkat.dtos.machineType;

import com.herkat.models.MachineType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineTypeDto {

    private Integer id;

    private String name;

    public static MachineTypeDto fromEntity(MachineType machineType) {
        return new MachineTypeDto(
                machineType.getId(),
                machineType.getName()
        );
    }

}
