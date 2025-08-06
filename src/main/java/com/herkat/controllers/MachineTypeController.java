package com.herkat.controllers;

import com.herkat.dtos.machineType.MachineTypeDto;
import com.herkat.dtos.machineType.NewMachineType;
import com.herkat.dtos.machineType.UpdateMachineTypeDto;
import com.herkat.services.MachineTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/machine-types")
public class MachineTypeController {

    private final MachineTypeService service;

    public MachineTypeController(MachineTypeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MachineTypeDto register(@Valid @RequestBody NewMachineType newMachineType) {
        return service.register(newMachineType);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MachineTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MachineTypeDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public MachineTypeDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MachineTypeDto update(@PathVariable Integer id,
                                 @Valid @RequestBody UpdateMachineTypeDto updateMachineTypeDto) {
        return service.update(id, updateMachineTypeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
