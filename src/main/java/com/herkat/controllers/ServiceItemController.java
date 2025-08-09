package com.herkat.controllers;

import com.herkat.dtos.serviceItem.NewServiceItemDto;
import com.herkat.dtos.serviceItem.ServiceItemDto;
import com.herkat.dtos.serviceItem.UpdateServiceItemDto;
import com.herkat.services.ServiceItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/service-items")
public class ServiceItemController {

    private final ServiceItemService service;

    public ServiceItemController(ServiceItemService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceItemDto register(@ModelAttribute @Valid NewServiceItemDto newServiceItemDto,
                                   @RequestPart("image") MultipartFile image) throws IOException {
        return service.register(newServiceItemDto, image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceItemDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name{name}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemDto update(@PathVariable Integer id,
                                 @ModelAttribute @Valid UpdateServiceItemDto updateServiceItemDto,
                                 @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return service.update(id, updateServiceItemDto, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

}
