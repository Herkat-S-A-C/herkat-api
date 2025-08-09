package com.herkat.validators;

import com.herkat.dtos.serviceItem.NewServiceItemDto;
import com.herkat.dtos.serviceItem.UpdateServiceItemDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.ServiceItemRepository;
import org.springframework.stereotype.Component;

@Component
public class ServiceItemValidator {

    private final ServiceItemRepository repository;

    public ServiceItemValidator(ServiceItemRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewServiceItemDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del servicio no puede estas vacío.");
        }

        if(dto.getTypeId() == null) {
            throw new BadRequestException("El tipo de servicio no puede estar vacío.");
        }

        if(dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new BadRequestException("La descripción del servicio no puede estar vacía.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del servicio ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateServiceItemDto dto) {
        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(existingServiceItem -> {
                    if(existingServiceItem.getId().equals(id)) {
                        throw new ConflictException("El nombre del servicio ya existe.");
                    }
                });
    }

}
