package com.herkat.controllers;

import com.herkat.dtos.maintenanceType.NewServiceItemType;
import com.herkat.dtos.maintenanceType.ServiceItemTypeDto;
import com.herkat.dtos.maintenanceType.UpdateServiceItemTypeDto;
import com.herkat.services.ServiceItemTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-item-type")
public class ServiceItemTypeController {

    private final ServiceItemTypeService service;

    public ServiceItemTypeController(ServiceItemTypeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceItemTypeDto register(@RequestBody @Valid NewServiceItemType newServiceItemType) {
        return service.register(newServiceItemType);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceItemTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemTypeDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemTypeDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceItemTypeDto update(@PathVariable Integer id,
                                     @RequestBody @Valid UpdateServiceItemTypeDto updateServiceItemTypeDto) {
        return service.update(id, updateServiceItemTypeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
