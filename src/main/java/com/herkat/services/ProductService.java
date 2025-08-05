package com.herkat.services;

import com.herkat.dtos.image.CloudinaryImage;
import com.herkat.dtos.image.NewImageDto;
import com.herkat.dtos.product.NewProductDto;
import com.herkat.dtos.product.ProductDto;
import com.herkat.dtos.product.UpdateProductDto;
import com.herkat.models.Image;
import com.herkat.models.Product;
import com.herkat.models.ProductType;
import com.herkat.repositories.ImageRepository;
import com.herkat.repositories.ProductRepository;
import com.herkat.repositories.ProductTypeRepository;
import com.herkat.validators.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTypeRepository typeRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final CloudinaryService cloudinaryService;
    private final ProductValidator validator;

    public ProductService(ProductRepository productRepository,
                          ProductTypeRepository typeRepository,
                          ImageRepository imageRepository,
                          ImageService imageService,
                          CloudinaryService cloudinaryService,
                          ProductValidator validator) {
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.cloudinaryService = cloudinaryService;
        this.validator = validator;
    }

    @Transactional
    public ProductDto register(NewProductDto newProductDto, MultipartFile image) throws IOException {
        // Validamos las reglas de negocio antes de registrar
        validator.validateBeforeRegister(newProductDto);

        // Buscamos el tipo de producto por su ID
        ProductType type = typeRepository.findById(newProductDto.getTypeId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Tipo de producto con ID: " + newProductDto.getTypeId() + " no encontrado."
                ));

        // Subimos la imagen a Cloudinary y la guardamos en la DB
        Image newImage = imageService.addImage(image);

        // Convertimos el DTO a entidad
        Product newProduct = NewProductDto.toEntity(
                newProductDto,
                type,
                newImage
        );

        // Guardamos la entidad en la DB
        Product savedProduct = productRepository.save(newProduct);

        return ProductDto.fromEntity(savedProduct);
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    public ProductDto findById(Integer id) {
        return productRepository.findById(id)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Producto con ID: " + id + " no encontrado."));
    }

    public ProductDto findByName(String name) {
        return productRepository.findByNameIgnoreCase(name)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Producto con nombre: " + name + " no encontrado."));
    }

    @Transactional
    public ProductDto update(Integer id, UpdateProductDto updateProductDto, MultipartFile newImage) throws IOException {
        // Validamos las reglas de negocio antes de actualizar
        validator.validateBeforeUpdate(id, updateProductDto);

        // Buscamos el producto por su ID
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto con ID: " + id + " no encontrado."));

        // Buscamos el tipo de producto por su ID si se proporcionó
        ProductType newType = null;
        if(updateProductDto.getTypeId() != null) {
            // Buscamos el tipo
            newType = typeRepository.findById(updateProductDto.getTypeId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Tipo de producto con ID: " + updateProductDto.getTypeId() + " no encontrado."
                    ));
        }

        // Manejamos la nueva imagen
        Image newImageEntity = null;
        if(newImage != null && !newImage.isEmpty()) {
            // Eliminamos la imagen de Cloudinary
            cloudinaryService.delete(existingProduct.getImage().getPublicId());

            // Subimos la nueva imagen a Cloudinary
            CloudinaryImage uploaded = cloudinaryService.upload(newImage);

            // Creamos la nueva entidad de la imagen
            Image imageEntity = NewImageDto.toEntity(
                    uploaded.getUrl(),
                    uploaded.getPublicId()
            );

            // Guardamos la nueva imagen en la DB
            newImageEntity = imageRepository.save(imageEntity);
        }

        // Creamos la entidad con los datos actualizados
        Product updatedProduct = UpdateProductDto.updateEntity(
                updateProductDto,
                existingProduct,
                newType,
                newImageEntity);

        // Guardamos la entidad actualizada en la DB
        Product savedProduct = productRepository.save(updatedProduct);

        // Convertimos la entidad a DTO para retornarlo
        return ProductDto.fromEntity(savedProduct);
    }

    @Transactional
    public void delete(Integer id) throws IOException {
        // Buscar el producto por su ID
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto con ID: " + id + " no encontrado."));

        // Eliminamos la imagen de Cloudinary
        cloudinaryService.delete(existingProduct.getImage().getPublicId());

        // Eliminamos el producto de la DB (la imagen se elimina en cascada por "orphanRemoval = true")
        productRepository.delete(existingProduct);
    }

}
