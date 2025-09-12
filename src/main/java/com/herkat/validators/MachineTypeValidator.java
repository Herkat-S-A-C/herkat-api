package com.herkat.validators;

import com.herkat.dtos.machine_type.NewMachineType;
import com.herkat.dtos.machine_type.UpdateMachineTypeDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.MachineTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class MachineTypeValidator {

    private final MachineTypeRepository repository;

    public MachineTypeValidator(MachineTypeRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewMachineType dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del tipo de máquina no puede estar vacío.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del tipo de máquina ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateMachineTypeDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del tipo de máquina no puede estar vacío.");
        }

        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(existingType -> {
                    if(!existingType.getId().equals(id)) {
                        throw new ConflictException("El nombre del tipo de máquina ya existe.");
                    }
                });
    }

}
