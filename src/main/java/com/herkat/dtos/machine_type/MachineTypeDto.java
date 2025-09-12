package com.herkat.dtos.machine_type;

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
