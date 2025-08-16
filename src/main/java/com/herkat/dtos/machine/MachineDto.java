package com.herkat.dtos.machine;

import com.herkat.models.Machine;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineDto {

    private Integer id;

    private String name;

    private String typeName;

    private String description;

    private String imageUrl;

    private Boolean isFeatured;

    public static MachineDto fromEntity(Machine machine) {
        return new MachineDto(
                machine.getId(),
                machine.getName(),
                machine.getType().getName(),
                machine.getDescription(),
                machine.getImage().getUrl(),
                machine.getIsFeatured()
        );
    }

}
