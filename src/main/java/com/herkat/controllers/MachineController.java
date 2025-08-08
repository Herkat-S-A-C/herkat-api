package com.herkat.controllers;

import com.herkat.dtos.machine.MachineDto;
import com.herkat.dtos.machine.NewMachineDto;
import com.herkat.dtos.machine.UpdateMachineDto;
import com.herkat.services.MachineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/machines")
public class MachineController {

    private final MachineService service;

    public MachineController(MachineService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MachineDto register(@ModelAttribute @Valid NewMachineDto newMachineDto,
                               @RequestPart("image") MultipartFile image) throws IOException {
        return service.register(newMachineDto, image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MachineDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MachineDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public MachineDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MachineDto update(@PathVariable Integer id,
                             @ModelAttribute @Valid UpdateMachineDto updateMachineDto,
                             @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return service.update(id, updateMachineDto, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

}
