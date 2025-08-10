package com.herkat.validators;

import com.herkat.dtos.banner.NewBannerDto;
import com.herkat.dtos.banner.UpdateBannerDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.BannerRepository;
import org.springframework.stereotype.Component;

@Component
public class BannerValidator {

    private final BannerRepository repository;

    public BannerValidator(BannerRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewBannerDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del banner no puede estar vacío.");
        }

        if(repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ConflictException("El nombre del banner ya existe.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateBannerDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del banner no puede estar vacío.");
        }

        repository.findByNameIgnoreCase(dto.getName())
                .ifPresent(existingType -> {
                    if(!existingType.getId().equals(id)) {
                        throw new ConflictException("El nombre del banner ya existe.");
                    }
                });
    }

}
