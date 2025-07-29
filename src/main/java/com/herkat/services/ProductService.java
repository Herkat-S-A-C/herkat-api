package com.herkat.services;

import com.cloudinary.utils.ObjectUtils;
import com.herkat.dtos.ProductRequestDTO;
import com.herkat.dtos.ProductResponseDTO;
import com.herkat.mappers.ProductMapper;
import com.herkat.models.Product;
import com.herkat.models.ProductType;
import com.herkat.repositories.ProductRepository;
import com.herkat.repositories.ProductTypeRepository;
import com.herkat.validators.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductTypeRepository typeRepository;
    private final ProductValidator validator;
    private final ProductMapper mapper;
    private final CloudinaryImageService cloudinaryService;

    public ProductService(ProductRepository repository,
                          ProductTypeRepository typeRepository,
                          ProductValidator validator,
                          ProductMapper mapper,
                          CloudinaryImageService cloudinaryService) {
        this.repository = repository;
        this.typeRepository = typeRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    public ProductResponseDTO register(ProductRequestDTO dto, MultipartFile imageFile) {
        validator.validateBeforeRegister(dto);

        // Subir imagen a Cloudinary
        Map<String, String> imageData;
        try {
            imageData = cloudinaryService.uploadImage(imageFile);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary", e);
        }

        Product product = mapper.toEntity(dto);
        product.setId(null);
        product.setImageUrl(imageData.get("secure_url"));
        product.setImagePublicId(imageData.get("public_id"));

        Product savedProduct = repository.save(product);

        return mapper.toDTO(savedProduct);
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No hay productos registrados.");
        }

        return products.stream().map(mapper::toDTO).toList();
    }

    public ProductResponseDTO findById(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado."));

        return mapper.toDTO(product);
    }

    public ProductResponseDTO findByName(String name) {
        Product product = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        return mapper.toDTO(product);
    }

    public ProductResponseDTO update(Integer id, ProductRequestDTO dto, MultipartFile image) {
        validator.validateBeforeUpdate(id, dto);

        Product existing = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado."));

        existing.setName(dto.getName());
        existing.setCapacity(dto.getCapacity());
        existing.setDescription(dto.getDescription());

        if (!existing.getType().getId().equals(dto.getTypeId())) {
            ProductType newType = typeRepository.findById(dto.getTypeId())
                    .orElseThrow(() -> new NoSuchElementException("Tipo de producto no encontrado"));
            existing.setType(newType);
        }

        if (image != null && !image.isEmpty()) {
            // Eliminar imagen antigua de Cloudinary si existe
            if (existing.getImagePublicId() != null) {
                try {
                    cloudinaryService.deleteImage(existing.getImagePublicId());
                } catch (IOException e) {
                    System.out.println("Error al eliminar imagen anterior de Cloudinary: " + e.getMessage());
                }
            }

            // Subir nueva imagen
            try {
                Map<String, String> imageData = cloudinaryService.uploadImage(image);
                existing.setImageUrl(imageData.get("secure_url"));
                existing.setImagePublicId(imageData.get("public_id"));
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imagen a Cloudinary", e);
            }
        }

        Product updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado."));

        // Eliminar imagen de Cloudinary
        if (product.getImagePublicId() != null) {
            try {
                cloudinaryService.deleteImage(product.getImagePublicId());
            } catch (IOException e) {
                System.out.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
            }
        }

        repository.delete(product);
    }

}
