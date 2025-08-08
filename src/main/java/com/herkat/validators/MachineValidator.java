package com.herkat.validators;

import com.herkat.dtos.machine.NewMachineDto;
import com.herkat.dtos.machine.UpdateMachineDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.MachineRepository;
import org.springframework.stereotype.Component;

@Component
public class MachineValidator {

    private final MachineRepository repository;

    public MachineValidator(MachineRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewMachineDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre de la máquina no puede estar vacío.");
        }

        if(dto.getTypeId() == null) {
            throw new BadRequestException("El tipo de la máquina no puede estar vacío.");
        }

        if(dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new BadRequestException("La descripción de la máquina no puede estar vacía.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre de la máquina ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateMachineDto dto) {
        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(existingMachine -> {
                    if(!existingMachine.getId().equals(id)) {
                        throw new ConflictException("El nombre de la máquina ya existe.");
                    }
                });
    }

}
