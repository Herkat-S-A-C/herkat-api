package com.herkat.controllers;

import com.herkat.dtos.ProductTypeRequestDTO;
import com.herkat.dtos.ProductTypeResponseDTO;
import com.herkat.services.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {

    private final ProductTypeService service;

    public ProductTypeController(ProductTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductTypeResponseDTO> register(@Valid @RequestBody ProductTypeRequestDTO requestDTO) {
        ProductTypeResponseDTO newType = service.register(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newType);
    }

    @GetMapping
    public ResponseEntity<List<ProductTypeResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductTypeResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductTypeResponseDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProductTypeResponseDTO> update(@PathVariable Integer id,
                                                          @Valid @RequestBody ProductTypeRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
