package com.herkat.controllers;

import com.herkat.dtos.client.ClientDto;
import com.herkat.dtos.client.NewClientDto;
import com.herkat.dtos.client.UpdateClientDto;
import com.herkat.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ClientController {

    private final ClientController service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto register(@RequestBody @Valid NewClientDto newClientDto){
        return service.register(newClientDto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    
    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @PutMapping(path = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto update(@PathVariable Integer id,
                             @RequestBody @Valid UpdateClientDto updateClientDto){
        return service.update(id, updateClientDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

}