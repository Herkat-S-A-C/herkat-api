package com.herkat.services;

import com.herkat.dtos.machine.MachineDto;
import com.herkat.dtos.machine.NewMachineDto;
import com.herkat.dtos.machine.UpdateMachineDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.Image;
import com.herkat.models.Machine;
import com.herkat.models.MachineType;
import com.herkat.repositories.MachineRepository;
import com.herkat.repositories.MachineTypeRepository;
import com.herkat.validators.MachineValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineTypeRepository typeRepository;
    private final ImageService imageService;
    private final ItemService itemService;
    private final MachineValidator machineValidator;

    public MachineService(MachineRepository machineRepository,
                          MachineTypeRepository typeRepository,
                          ImageService imageService,
                          ItemService itemService,
                          MachineValidator machineValidator) {
        this.machineRepository = machineRepository;
        this.typeRepository = typeRepository;
        this.imageService = imageService;
        this.itemService = itemService;
        this.machineValidator = machineValidator;
    }

    @Transactional
    public MachineDto register(NewMachineDto newMachineDto, MultipartFile image) throws IOException {
        // Validamos las reglas de negocio antes de registrar
        machineValidator.validateNameUniqueness(newMachineDto.getName());

        // Buscamos el tipo de máquina por su ID
        MachineType type = typeRepository.findById(newMachineDto.getTypeId())
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_TYPE_NOT_FOUND));

        // Subimos la imagen a S3 y a la DB
        Image savedImage = imageService.addImageEntity(image);

        // Convertimos el DTO a entidad
        Machine newMachine = NewMachineDto.toEntity(
                newMachineDto,
                type,
                savedImage
        );

        // Guardamos en la base de datos
        Machine savedMachine = machineRepository.save(newMachine);
        itemService.createItemForMachine(savedMachine);

        // Convertimos la entidad a DTO para retornarlo
        return MachineDto.fromEntity(savedMachine);
    }

    @Transactional(readOnly = true)
    public List<MachineDto> findAll() {
        // Buscamos todas las máquinas
        return machineRepository.findAll()
                .stream()
                .map(MachineDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MachineDto findById(Integer id) {
        // Buscamos la máquina por su ID
        return machineRepository.findById(id)
                .map(MachineDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_NOT_FOUND));
    }

    public MachineDto findByName(String name) {
        // Buscamos la máquina por su nombre
        return machineRepository.findByNameIgnoreCase(name)
                .map(MachineDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_NOT_FOUND));
    }

    @Transactional
    public MachineDto update(Integer id, UpdateMachineDto updateMachineDto, MultipartFile newImage) throws IOException {
        // Validamos las reglas de negocio antes de actualizar
        machineValidator.validateNameOnUpdate(id, updateMachineDto.getName());

        // Buscamos la máquina por su ID
        Machine existingMachine = machineRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_NOT_FOUND));

        // Buscamos el tipo de máquina por su ID si se proporcionó
        MachineType newType = null;
        if(updateMachineDto.getTypeId() != null) {
            // Buscamos el tipo
            newType = typeRepository.findById(existingMachine.getType().getId())
                    .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_TYPE_NOT_FOUND));
        }

        // Manejamos la nueva imagen
        Image newImageEntity = null;
        if(newImage != null && !newImage.isEmpty()) {
            // Subimos y guardamos la imagen en S3 y la DB
            newImageEntity = imageService.updateImageEntity(existingMachine.getImage().getId(), newImage);
        }

        // Creamos la entidad con los datos actualizados
        Machine updatedMachine = UpdateMachineDto.updateEntity(
                updateMachineDto,
                existingMachine,
                newType,
                newImageEntity
        );

        // Guardamos la entidad en la base de datos
        Machine savedMachine = machineRepository.save(updatedMachine);

        // Convertimos la entidad a DTO para retornarlo
        return MachineDto.fromEntity(savedMachine);
    }

    @Transactional
    public void delete(Integer id) throws IOException {
        // Buscamos la máquina por su ID
        Machine existingMachine = machineRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MACHINE_NOT_FOUND));

        // Eliminamos la imagen de S3 (pero no de la DB)
        imageService.delete(existingMachine.getImage().getId());

        // Eliminamos la máquina de la DB -> Hibernate elimina también la imagen de la DB
        machineRepository.delete(existingMachine);
    }

}
