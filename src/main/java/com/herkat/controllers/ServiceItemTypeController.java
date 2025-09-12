package com.herkat.controllers;

import com.herkat.dtos.service_item_type.NewServiceItemType;
import com.herkat.dtos.service_item_type.ServiceItemTypeDto;
import com.herkat.dtos.service_item_type.UpdateServiceItemTypeDto;
import com.herkat.services.ServiceItemTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-item-types")
public class ServiceItemTypeController {

    private final ServiceItemTypeService service;

    public ServiceItemTypeController(ServiceItemTypeService service) {
        this.service = service;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceItemTypeDto register(@RequestBody @Valid NewServiceItemType newServiceItemType) {
        return service.register(newServiceItemType);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceItemTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemTypeDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemTypeDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemTypeDto update(@PathVariable Integer id,
                                     @RequestBody @Valid UpdateServiceItemTypeDto updateServiceItemTypeDto) {
        return service.update(id, updateServiceItemTypeDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
