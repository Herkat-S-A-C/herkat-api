package com.herkat.validators;

import com.herkat.dtos.service_item_type.NewServiceItemType;
import com.herkat.dtos.service_item_type.UpdateServiceItemTypeDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.ServiceItemTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class ServiceItemTypeValidator {

    private final ServiceItemTypeRepository repository;

    public ServiceItemTypeValidator(ServiceItemTypeRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewServiceItemType dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del tipo de servicio no puede estar vacío.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del tipo de servicio ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateServiceItemTypeDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del tipo de servicio no puede estar vacío.");
        }

        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(existingType -> {
                    if(!existingType.getId().equals(id)){
                        throw new ConflictException("El nombre del tipo de servicio ya existe.");
                    }
                });
    }

}
