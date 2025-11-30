package com.herkat.controllers;

import com.herkat.dtos.inventory_movement.InventoryMovementDto;
import com.herkat.dtos.inventory_movement.NewInventoryMovementDto;
import com.herkat.dtos.inventory_movement.UpdateInventoryMovementDto;
import com.herkat.models.MovementType;
import com.herkat.services.InventoryMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/inventory-movements")
public class InventoryMovementController {

    private final InventoryMovementService service;

    public InventoryMovementController(InventoryMovementService service) {
        this.service = service;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryMovementDto register(@RequestBody @Valid NewInventoryMovementDto newInventoryMovementDto) {
        return service.register(newInventoryMovementDto);
    }

    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public InventoryMovementDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryMovementDto> findByItemId(@PathVariable Integer itemId) {
        return service.findByItemId(itemId);
    }

    @GetMapping("/date-range")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryMovementDto> findByDateRange(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return service.findByDateRange(start.atStartOfDay(), end.atTime(23, 59, 59));
    }

    @GetMapping("/type")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryMovementDto> findByMovementType(@RequestParam MovementType movementType) {
        return service.findByMovementType(movementType);
    }

    @GetMapping("/item/{itemId}/date-range")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryMovementDto> findByItemIdAndDateBetween(@PathVariable Integer itemId,
                                                                 @RequestParam LocalDate start,
                                                                 @RequestParam LocalDate end) {
        return service.findByItemIdAndDateBetween(itemId, start.atStartOfDay(), end.atTime(23, 59, 59));
    }

    @GetMapping("/item/{itemId}/last")
    @ResponseStatus(HttpStatus.OK)
    public InventoryMovementDto findTopByItemIdOrderByDateDesc(@PathVariable Integer itemId) {
        return service.findTopByItemIdOrderByDateDesc(itemId);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryMovementDto update(@PathVariable Integer id, @RequestBody @Valid UpdateInventoryMovementDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
