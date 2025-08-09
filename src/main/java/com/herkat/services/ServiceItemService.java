package com.herkat.services;

import com.herkat.dtos.serviceItem.NewServiceItemDto;
import com.herkat.dtos.serviceItem.ServiceItemDto;
import com.herkat.dtos.serviceItem.UpdateServiceItemDto;
import com.herkat.models.Image;
import com.herkat.models.ServiceItem;
import com.herkat.models.ServiceItemType;
import com.herkat.repositories.ServiceItemRepository;
import com.herkat.repositories.ServiceItemTypeRepository;
import com.herkat.validators.ServiceItemValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServiceItemService {

    private final ServiceItemRepository serviceItemRepository;
    private final ServiceItemTypeRepository typeRepository;
    private final ImageService imageService;
    private final ServiceItemValidator serviceItemValidator;

    public ServiceItemService(ServiceItemRepository serviceItemRepository,
                              ServiceItemTypeRepository typeRepository,
                              ImageService imageService,
                              ServiceItemValidator serviceItemValidator) {
        this.serviceItemRepository = serviceItemRepository;
        this.typeRepository = typeRepository;
        this.imageService = imageService;
        this.serviceItemValidator = serviceItemValidator;
    }

    @Transactional
    public ServiceItemDto register(NewServiceItemDto newServiceItemDto, MultipartFile image) throws IOException {
        // Validamos las reglas de negocio antes de registrar
        serviceItemValidator.validateBeforeRegister(newServiceItemDto);

        // Buscamos el tipo de servicio por su ID
        ServiceItemType type = typeRepository.findById(newServiceItemDto.getTypeId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Tipo de servicio con ID: " + newServiceItemDto.getTypeId() + " no encontrado."
                ));

        // Subimos la imagen a Cloudinary y la DB
        Image savedImage = imageService.addImageEntity(image);

        // Convertimos el DTO a entidad
        ServiceItem newServiceItem = NewServiceItemDto.toEntity(
                newServiceItemDto,
                type,
                savedImage
        );

        // Guardamos en la base de datos
        ServiceItem savedServiceItem = serviceItemRepository.save(newServiceItem);

        // Convertimos la entidad a DTO para retornarlo
        return ServiceItemDto.fromEntity(savedServiceItem);
    }

    public List<ServiceItemDto> findAll() {
        // Buscamos todos los servicios
        return serviceItemRepository.findAll()
                .stream()
                .map(ServiceItemDto::fromEntity)
                .toList();
    }

    public ServiceItemDto findById(Integer id) {
        // Buscamos el servicio por su ID
        return serviceItemRepository.findById(id)
                .map(ServiceItemDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Servicio con ID: " + id + " no encontrado."));
    }

    public ServiceItemDto findByName(String name) {
        // Buscamos el servicio por su nombre
        return serviceItemRepository.findByNameIgnoreCase(name)
                .map(ServiceItemDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Servicio con nombre: " + name + " no encontrado."));
    }

    @Transactional
    public ServiceItemDto update(Integer id, UpdateServiceItemDto updateServiceItemDto, MultipartFile newImage) throws IOException {
        // Validamos las reglas de negocio antes de actualizar
        serviceItemValidator.validateBeforeUpdate(id, updateServiceItemDto);

        // Buscamos el servicio por su ID
        ServiceItem existingServiceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio con ID: " + id + " no encontrado."));

        // Buscamos el tipo de servicio por su ID si se proporcionó
        ServiceItemType newType = null;
        if(updateServiceItemDto.getTypeId() != null) {
            // Buscamos el tipo
            newType = typeRepository.findById(updateServiceItemDto.getTypeId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Tipo de servicio con ID: " + updateServiceItemDto.getTypeId() + " no encontrado."
                    ));
        }

        // Manejamos la nueva imagen
        Image newImageEntity = null;
        if(newImage != null && !newImage.isEmpty()) {
            // Subimos y guardamos la imagen a Cloudinary y la DB
            newImageEntity = imageService.updateImageEntity(existingServiceItem.getImage().getId(), newImage);
        }

        // Creamos la entidad con los datos actualizados
        ServiceItem updatedServiceItem = UpdateServiceItemDto.updateEntity(
                updateServiceItemDto,
                existingServiceItem,
                newType,
                newImageEntity
        );

        // Guardamos la entidad en la base de datos
        ServiceItem savedServiceItem = serviceItemRepository.save(updatedServiceItem);

        // Convertimos la entidad a DTO para retornarlo
        return ServiceItemDto.fromEntity(savedServiceItem);
    }

    @Transactional
    public void delete(Integer id) throws IOException {
        // Buscamos el servicio por su ID
        ServiceItem existingServiceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio con ID: " + id + " no encontrado."));

        // Eliminamos la imagen de Cloudinary y la DB
        imageService.delete(existingServiceItem.getImage().getId());

        // Eliminamos la máquina de la DB
        serviceItemRepository.delete(existingServiceItem);
    }

}
