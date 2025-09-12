package com.herkat.controllers;

import com.herkat.dtos.product_type.NewProductTypeDto;
import com.herkat.dtos.product_type.ProductTypeDto;
import com.herkat.dtos.product_type.UpdateProductTypeDto;
import com.herkat.services.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {

    private final ProductTypeService service;

    public ProductTypeController(ProductTypeService service) {
        this.service = service;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductTypeDto register(@Valid @RequestBody NewProductTypeDto requestDTO) {
        return service.register(requestDTO);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public ProductTypeDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ProductTypeDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductTypeDto update(@PathVariable Integer id,
                                 @Valid @RequestBody UpdateProductTypeDto updateProductTypeDto) {
        return service.update(id, updateProductTypeDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
