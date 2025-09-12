package com.herkat.services;

import com.herkat.dtos.product.NewProductDto;
import com.herkat.dtos.product.ProductDto;
import com.herkat.dtos.product.UpdateProductDto;
import com.herkat.models.Image;
import com.herkat.models.Product;
import com.herkat.models.ProductType;
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
    private final ImageService imageService;
    private final ProductValidator validator;

    public ProductService(ProductRepository productRepository,
                          ProductTypeRepository typeRepository,
                          ImageService imageService,
                          ProductValidator validator) {
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
        this.imageService = imageService;
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

        // Subimos la imagen a S3 y a la guardamos en la DB
        Image savedImage = imageService.addImageEntity(image);

        // Convertimos el DTO del producto a entidad
        Product newProduct = NewProductDto.toEntity(
                newProductDto,
                type,
                savedImage
        );

        // Guardamos la entidad en la DB
        Product savedProduct = productRepository.save(newProduct);

        return ProductDto.fromEntity(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        // Buscamos todos los productos
        return productRepository.findAll()
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Integer id) {
        // Buscamos el producto por su ID
        return productRepository.findById(id)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Producto con ID: " + id + " no encontrado."));
    }

    public ProductDto findByName(String name) {
        // Buscamos el producto por su nombre
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
        if (updateProductDto.getTypeId() != null) {
            // Buscamos el tipo
            newType = typeRepository.findById(updateProductDto.getTypeId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Tipo de producto con ID: " + updateProductDto.getTypeId() + " no encontrado."
                    ));
        }

        // Manejamos la nueva imagen
        Image newImageEntity = null;
        if (newImage != null && !newImage.isEmpty()) {
            // Subimos y guardamos la nueva imagen en S3 y la DB
            newImageEntity = imageService.updateImageEntity(existingProduct.getImage().getId(), newImage);
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
        // Buscamos el producto por su ID
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto con ID: " + id + " no encontrado."));

        // Eliminamos la imagen de S3 (pero no de la DB)
        imageService.delete(existingProduct.getImage().getId());

        // Eliminamos el producto de la DB -> Hibernate elimina también la imagen de la DB
        productRepository.delete(existingProduct);
    }

}
