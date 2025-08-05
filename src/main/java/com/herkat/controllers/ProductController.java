package com.herkat.controllers;

import com.herkat.dtos.product.NewProductDto;
import com.herkat.dtos.product.ProductDto;
import com.herkat.dtos.product.UpdateProductDto;
import com.herkat.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto register(@ModelAttribute @Valid NewProductDto newProductDto,
                               @RequestPart("image") MultipartFile image) throws IOException {
        return service.register(newProductDto, image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto update(@PathVariable Integer id,
                             @ModelAttribute @Valid UpdateProductDto updateProductDto,
                             @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return service.update(id, updateProductDto, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

}
