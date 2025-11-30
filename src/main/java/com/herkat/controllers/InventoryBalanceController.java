package com.herkat.controllers;

import com.herkat.dtos.inventory_balance.InventoryBalanceDto;
import com.herkat.services.InventoryBalanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory-balance")
public class InventoryBalanceController {

    private final InventoryBalanceService service;

    public InventoryBalanceController(InventoryBalanceService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryBalanceDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public InventoryBalanceDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/item/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryBalanceDto findByItemId(@PathVariable Integer id) {
        return service.findByItemId(id);
    }

}
