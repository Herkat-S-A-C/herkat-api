package com.herkat.services;

import com.herkat.dtos.banner.BannerDto;
import com.herkat.dtos.banner.NewBannerDto;
import com.herkat.dtos.banner.UpdateBannerDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.Banner;
import com.herkat.models.Image;
import com.herkat.repositories.BannerRepository;
import com.herkat.validators.BannerValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final ImageService imageService;
    private final BannerValidator bannerValidator;

    public BannerService(BannerRepository bannerRepository,
                         ImageService imageService,
                         BannerValidator bannerValidator) {
        this.bannerRepository = bannerRepository;
        this.imageService = imageService;
        this.bannerValidator = bannerValidator;
    }

    @Transactional
    public BannerDto register(NewBannerDto newBannerDto, MultipartFile image) throws IOException {
        // Validamos las reglas de negocio antes de registrar
        bannerValidator.validateNameUniqueness(newBannerDto.getName());

        // Subimos la imagen a S3 y a la DB
        Image savedImage = imageService.addImageEntity(image);

        // Convertimos el DTO a entidad
        Banner newBanner = NewBannerDto.toEntity(
                newBannerDto,
                savedImage
        );

        // Guardamos en la base de datos
        Banner savedBanner = bannerRepository.save(newBanner);

        // Convertimos la entidad a DTO para retornarlo
        return BannerDto.fromEntity(savedBanner);
    }

    @Transactional(readOnly = true)
    public List<BannerDto> findAll() {
        // Buscamos todos los banners
        return bannerRepository.findAll()
                .stream()
                .map(BannerDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public BannerDto findById(Integer id) {
        // Buscamos el banner por su ID
        return bannerRepository.findById(id)
                .map(BannerDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.BANNER_NOT_FOUND));
    }

    @Transactional
    public BannerDto update(Integer id, UpdateBannerDto updateBannerDto, MultipartFile newImage) throws IOException {
        // Validamos las reglas de negocio antes de actualizar
        bannerValidator.validateNameOnUpdate(id, updateBannerDto.getName());

        // Buscamos el banner por su ID
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.BANNER_NOT_FOUND));

        // Manejamos la nueva imagen
        Image newImageEntity = null;
        if(newImage != null && !newImage.isEmpty()) {
            // Subimos y guardamos la imagen en S3 y la DB
            newImageEntity = imageService.updateImageEntity(existingBanner.getImage().getId(), newImage);
        }

        // Creamos la entidad con los datos actualizados
        Banner updatedBanner = UpdateBannerDto.updateEntity(
                updateBannerDto,
                existingBanner,
                newImageEntity
        );

        // Guardamos la entidad en la base de datos
        Banner savedBanner = bannerRepository.save(updatedBanner);

        // Convertimos la entidad a DTO para retornarlo
        return BannerDto.fromEntity(savedBanner);
    }

    @Transactional
    public void delete(Integer id) throws IOException {
        // Buscamos el banner por su ID
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.BANNER_NOT_FOUND));

        // Eliminamos la imagen de S3 y la DB
        imageService.delete(existingBanner.getImage().getId());

        // Eliminamos la máquina de la DB
        bannerRepository.delete(existingBanner);
    }

}
