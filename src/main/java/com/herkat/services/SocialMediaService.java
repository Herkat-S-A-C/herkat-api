package com.herkat.services;

import com.herkat.dtos.social_media.SocialMediaDto;
import com.herkat.dtos.social_media.UpdateSocialMediaDto;
import com.herkat.models.SocialMedia;
import com.herkat.models.SocialMediaType;
import com.herkat.repositories.SocialMediaRepository;
import com.herkat.validators.SocialMediaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SocialMediaService {

    private final SocialMediaRepository repository;
    private final SocialMediaValidator validator;

    public SocialMediaService(SocialMediaRepository repository, SocialMediaValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public SocialMediaDto update(SocialMediaType type, UpdateSocialMediaDto updateSocialMediaDto) {
        // Validamos las reglas de negocio antes de actualizar
        validator.validateBeforeUpdate(updateSocialMediaDto);

        // Buscamos la red social por su Tipo
        SocialMedia socialMedia = repository.findByType(type)
                .orElseThrow(() -> new NoSuchElementException("No se encontró la red social: " + type));

        // Actualizamos los datos de la red social
        SocialMedia updatedSocialMedia = UpdateSocialMediaDto.updateEntity(updateSocialMediaDto, socialMedia);

        // Guardamos la nueva entidad en la base de datos
        SocialMedia savedSocialMedia = repository.save(updatedSocialMedia);

        // Convertimos la entidad a DTO para retornarlo
        return SocialMediaDto.fromEntity(savedSocialMedia);
    }

    @Transactional(readOnly = true)
    public List<SocialMediaDto> findAll() {
        // Buscamos todas las redes sociales
        return repository.findAll()
                .stream()
                .map(SocialMediaDto::fromEntity)
                .toList();
    }

}
