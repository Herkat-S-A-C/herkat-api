package com.herkat.services;

import com.herkat.dtos.service_item.NewServiceItemDto;
import com.herkat.dtos.service_item.ServiceItemDto;
import com.herkat.dtos.service_item.UpdateServiceItemDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
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
        serviceItemValidator.validateNameUniqueness(newServiceItemDto.getName());

        // Buscamos el tipo de servicio por su ID
        ServiceItemType type = typeRepository.findById(newServiceItemDto.getTypeId())
                .orElseThrow(() -> new HerkatException(ErrorMessage.SERVICE_ITEM_TYPE_NOT_FOUND));

        // Subimos la imagen a S3 y la DB
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

    @Transactional(readOnly = true)
    public List<ServiceItemDto> findAll() {
        // Buscamos todos los servicios
        return serviceItemRepository.findAll()
                .stream()
                .map(ServiceItemDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceItemDto findById(Integer id) {
        // Buscamos el servicio por su ID
        return serviceItemRepository.findById(id)
                .map(ServiceItemDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.SERVICE_ITEM_NOT_FOUND));
    }

    public ServiceItemDto findByName(String name) {
        // Buscamos el servicio por su nombre
        return serviceItemRepository.findByNameIgnoreCase(name)
                .map(ServiceItemDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.SERVICE_ITEM_NOT_FOUND));
    }

    @Transactional
    public ServiceItemDto update(Integer id, UpdateServiceItemDto updateServiceItemDto, MultipartFile newImage) throws IOException {
        // Validamos las reglas de negocio antes de actualizar
        serviceItemValidator.validateNameOnUpdate(id, updateServiceItemDto.getName());

        // Buscamos el servicio por su ID
        ServiceItem existingServiceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.SERVICE_ITEM_NOT_FOUND));

        // Buscamos el tipo de servicio por su ID si se proporcionó
        ServiceItemType newType = null;
        if(updateServiceItemDto.getTypeId() != null) {
            // Buscamos el tipo
            newType = typeRepository.findById(updateServiceItemDto.getTypeId())
                    .orElseThrow(() -> new HerkatException(ErrorMessage.SERVICE_ITEM_TYPE_NOT_FOUND));
        }

        // Manejamos la nueva imagen
        Image newImageEntity = null;
        if(newImage != null && !newImage.isEmpty()) {
            // Subimos y guardamos la imagen a S3 y la DB
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
                .orElseThrow(() -> new HerkatException(ErrorMessage.SERVICE_ITEM_NOT_FOUND));

        // Eliminamos la imagen de S3 (pero no de la DB)
        imageService.delete(existingServiceItem.getImage().getId());

        // Eliminamos la máquina de la DB -> Hibernate elimina también la imagen de la DB
        serviceItemRepository.delete(existingServiceItem);
    }

}
