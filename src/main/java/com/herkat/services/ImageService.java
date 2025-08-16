package com.herkat.services;

import com.herkat.dtos.image.CloudinaryImage;
import com.herkat.dtos.image.ImageDto;
import com.herkat.dtos.image.NewImageDto;
import com.herkat.dtos.image.UpdateImageDto;
import com.herkat.models.Image;
import com.herkat.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    public ImageService(ImageRepository imageRepository, CloudinaryService cloudinaryService) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
    }


    // ********************* MÉTODOS INTERNOS PARA EL SERVICE ********************* //

    protected Image addImageEntity(MultipartFile file) throws IOException {
        // Subimos la imagen a Cloudinary
        CloudinaryImage uploaded = cloudinaryService.upload(file);

        // Creamos la nueva entidad
        Image newImage = NewImageDto.toEntity(uploaded.getUrl(), uploaded.getPublicId());

        // Guardamos en la base de datos
        return imageRepository.save(newImage);
    }

    protected Image updateImageEntity(Integer id, MultipartFile newFile) throws IOException {
        // Buscamos la imagen por su ID
        Image existing = imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Imagen con ID: " + id + " no encontrada."));

        // Borramos la imagen anterior en Cloudinary
        cloudinaryService.delete(existing.getPublicId());

        // Subimos la nueva imagen
        CloudinaryImage newImage = cloudinaryService.upload(newFile);

        // Creamos la nueva entidad con los datos actualizados
        Image updatedImage = UpdateImageDto.updateEntity(existing, newImage.getUrl(), newImage.getPublicId());

        // Guardamos en la DB
        return imageRepository.save(updatedImage);
    }


    // ********************* MÉTODOS PÚBLICOS PARA EL CONTROLLER ********************* //

    public ImageDto addImage(MultipartFile file) throws IOException {
        // Convertimos la entidad a DTO para retornarlo
        return ImageDto.fromEntity(addImageEntity(file));
    }

    public List<ImageDto> findAll() {
        // Buscamos todas las imágenes
        return imageRepository.findAll()
                .stream()
                .map(ImageDto::fromEntity)
                .toList();
    }

    public ImageDto findById(Integer id) {
        // Buscamos la imágen por su ID
        return imageRepository.findById(id)
                .map(ImageDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Imagen con ID: " + id + " no encontrada."));
    }

    public ImageDto update(Integer id, MultipartFile newFile) throws IOException {
        // Convertimos entidad a DTO para retornarlo
        return ImageDto.fromEntity(updateImageEntity(id, newFile));
    }

    public void delete(Integer id) throws IOException {
        // Buscamos la imagen por su ID
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Imagen no encontrada"));

        // Borramos la imagen de Cloudinary
        cloudinaryService.delete(image.getPublicId());

        // ❌ Ya no borramos la imagen en la base de datos (eso lo hace Hibernate por cascade)
    }

}
