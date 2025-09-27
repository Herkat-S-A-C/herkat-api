package com.herkat.services;

import com.herkat.dtos.machine_type.MachineTypeDto;
import com.herkat.dtos.machine_type.NewMachineTypeDto;
import com.herkat.dtos.machine_type.UpdateMachineTypeDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.MachineType;
import com.herkat.repositories.MachineTypeRepository;
import com.herkat.validators.MachineTypeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MachineTypeService {

    private final MachineTypeRepository repository;
    private final MachineTypeValidator validator;

    public MachineTypeService(MachineTypeRepository repository, MachineTypeValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public MachineTypeDto register(NewMachineTypeDto newMachineTypeDto) {
        // Validamos las reglas de negocio antes de registrar
        validator.validateNameUniqueness(newMachineTypeDto.getName());

        // Convertimos el DTO a entidad
        MachineType newType = NewMachineTypeDto.toEntity(newMachineTypeDto);

        // Guardamos el nuevo tipo en la base de datos
        MachineType savedType = repository.save(newType);

        // Convertimos la entidad a DTo para retornarlo
        return MachineTypeDto.fromEntity(savedType);
    }

    @Transactional(readOnly = true)
    public List<MachineTypeDto> findAll() {
        // Buscamos todos los tipos de máquina
        return repository.findAll()
                .stream()
                .map(MachineTypeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MachineTypeDto findById(Integer id) {
        // Buscar tipo de máquina por su ID
        return repository.findById(id)
                .map(MachineTypeDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_TYPE_NOT_FOUND));
    }

    public MachineTypeDto findByName(String name) {
        // Buscar tipo de máquina por su nombre
        return repository.findByNameIgnoreCase(name)
                .map(MachineTypeDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_TYPE_NOT_FOUND));
    }

    @Transactional
    public MachineTypeDto update(Integer id, UpdateMachineTypeDto updateMachineTypeDto) {
        // Validamos las reglas de negocio antes de actualizar
        validator.validateNameOnUpdate(id, updateMachineTypeDto.getName());

        // Buscamos el tipo de máquina por su ID
        MachineType existingType = repository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_TYPE_NOT_FOUND));

        // Convertimos el DTO a entidad
        MachineType updatedType = UpdateMachineTypeDto.updateEntity(updateMachineTypeDto, existingType);

        // Guardamos los cambios en la base de datos
        MachineType savedType = repository.save(updatedType);

        // Convertimos la entidad a DTO para retornarlo
        return MachineTypeDto.fromEntity(savedType);
    }

    @Transactional
    public void delete(Integer id) {
        // Buscamos el tipo de máquina por su ID
        MachineType existingType = repository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_TYPE_NOT_FOUND));

        // Eliminamos el tipo de máquina de la base de datos
        repository.delete(existingType);
    }

}
