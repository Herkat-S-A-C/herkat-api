package com.herkat.services;

import com.herkat.dtos.service_item_type.NewServiceItemType;
import com.herkat.dtos.service_item_type.ServiceItemTypeDto;
import com.herkat.dtos.service_item_type.UpdateServiceItemTypeDto;
import com.herkat.models.ServiceItemType;
import com.herkat.repositories.ServiceItemTypeRepository;
import com.herkat.validators.ServiceItemTypeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServiceItemTypeService {

    private final ServiceItemTypeRepository repository;
    private final ServiceItemTypeValidator validator;

    public ServiceItemTypeService(ServiceItemTypeRepository repository, ServiceItemTypeValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public ServiceItemTypeDto register(NewServiceItemType newServiceItemType) {
        // Validamos las reglas de negocio antes de registrar
        validator.validateBeforeRegister(newServiceItemType);

        // Convertimos el DTO a entidad
        ServiceItemType newType = NewServiceItemType.toEntity(newServiceItemType);

        // Guardamos en la base de datos
        ServiceItemType savedType = repository.save(newType);

        // Convertimos la entidad a DTO para retornarlo
        return ServiceItemTypeDto.fromEntity(savedType);
    }

    @Transactional(readOnly = true)
    public List<ServiceItemTypeDto> findAll() {
        // Buscamos todos los tipos de servicio
        return repository.findAll()
                .stream()
                .map(ServiceItemTypeDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceItemTypeDto findById(Integer id) {
        // Buscamos el tipo de servicio por su ID
        return repository.findById(id)
                .map(ServiceItemTypeDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Tipo de servicio con ID: " + id + " no encontrado."));
    }

    public ServiceItemTypeDto findByName(String name) {
        // Buscamos el tipo de servicio por su nombre
        return repository.findByNameIgnoreCase(name)
                .map(ServiceItemTypeDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Tipo de servicio con nombre: " + name + " no encontrado."));
    }

    @Transactional
    public ServiceItemTypeDto update(Integer id, UpdateServiceItemTypeDto updateServiceItemTypeDto) {
        // Validamos las reglas de negocio antes de actualizar
        validator.validateBeforeUpdate(id, updateServiceItemTypeDto);

        // Buscamos el tipo de servicio por su ID
        ServiceItemType existingType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de servicio con ID: " + id + " no encontrado."));

        // Creamos la nueva instancia con los campos actualizados
        ServiceItemType updatedType = UpdateServiceItemTypeDto.updateEntity(updateServiceItemTypeDto, existingType);

        // Guardamos los cambios en la base de datos
        ServiceItemType savedType = repository.save(updatedType);

        // Convertimos la entidad a DTO para retornarlo
        return ServiceItemTypeDto.fromEntity(savedType);
    }

    @Transactional
    public void delete(Integer id) {
        // Buscamos el tipo de servicio por su ID
        ServiceItemType existingType = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de servicio con ID: " + id + " no encontrado."));

        // Eliminamos el tipo de servicio de la base de datos
        repository.delete(existingType);
    }

}
