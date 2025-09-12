package com.herkat.services;

import com.herkat.config.aws.S3Service;
import com.herkat.dtos.image.ImageDto;
import com.herkat.dtos.image.NewImageDto;
import com.herkat.dtos.image.S3File;
import com.herkat.dtos.image.UpdateImageDto;
import com.herkat.models.Image;
import com.herkat.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    public ImageService(ImageRepository imageRepository, S3Service s3Service) {
        this.imageRepository = imageRepository;
        this.s3Service = s3Service;
    }


    // ********************* MÉTODOS INTERNOS PARA EL SERVICE ********************* //

    @Transactional
    protected Image addImageEntity(MultipartFile file) throws IOException {
        // Subimos la imagen a S3
        S3File uploaded = s3Service.uploadFile(file); // Aquí se transporta tanto la url como la key

        // Creamos la nueva entidad
        Image newImage = NewImageDto.toEntity(uploaded.url(), uploaded.key());

        // Guardamos en la base de datos
        return imageRepository.save(newImage);
    }

    @Transactional
    protected Image updateImageEntity(Integer id, MultipartFile newFile) throws IOException {
        // Buscamos la imagen por su ID
        Image existing = imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Imagen con ID: " + id + " no encontrada."));

        // Borramos la imagen anterior en S3
        s3Service.deleteFile(existing.getS3Key());

        // Subimos la nueva imagen
        S3File uploaded = s3Service.uploadFile(newFile);

        // Actualizamos entidad con los nuevos datos
        Image updatedImage = UpdateImageDto.updateEntity(existing, uploaded.url(), uploaded.key());

        // Guardamos en la DB
        return imageRepository.save(updatedImage);
    }


    // ********************* MÉTODOS PÚBLICOS PARA EL CONTROLLER ********************* //

    @Transactional
    public ImageDto addImage(MultipartFile file) throws IOException {
        // Convertimos la entidad a DTO para retornarlo
        return ImageDto.fromEntity(addImageEntity(file));
    }

    @Transactional(readOnly = true)
    public List<ImageDto> findAll() {
        // Buscamos todas las imágenes
        return imageRepository.findAll()
                .stream()
                .map(ImageDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ImageDto findById(Integer id) {
        // Buscamos la imágen por su ID
        return imageRepository.findById(id)
                .map(ImageDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Imagen con ID: " + id + " no encontrada."));
    }

    @Transactional
    public ImageDto updateImage(Integer id, MultipartFile newFile) throws IOException {
        // Convertimos entidad a DTO para retornarlo
        return ImageDto.fromEntity(updateImageEntity(id, newFile));
    }

    @Transactional
    public void delete(Integer id) throws IOException {
        // Buscamos la imagen por su ID
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Imagen no encontrada"));

        // Borramos la imagen de S3
        s3Service.deleteFile(image.getS3Key());

        // No borramos la imagen en la base de datos (eso lo hace Hibernate por cascade)
    }

}
