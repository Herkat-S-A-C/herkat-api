package com.herkat.services;

import com.herkat.dtos.inventory_balance.UpdateInventoryBalanceDto;
import com.herkat.dtos.inventory_movement.InventoryMovementDto;
import com.herkat.dtos.inventory_movement.NewInventoryMovementDto;
import com.herkat.dtos.inventory_movement.UpdateInventoryMovementDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.InventoryBalance;
import com.herkat.models.InventoryMovement;
import com.herkat.models.Item;
import com.herkat.models.MovementType;
import com.herkat.repositories.InventoryBalanceRepository;
import com.herkat.repositories.InventoryMovementRepository;
import com.herkat.validators.InventoryMovementValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryMovementService {

    private final InventoryMovementRepository movementRepository;
    private final InventoryBalanceRepository balanceRepository;
    private final InventoryMovementValidator validator;

    public InventoryMovementService(InventoryMovementRepository movementRepository,
                                    InventoryBalanceRepository balanceRepository,
                                    InventoryMovementValidator validator) {
        this.movementRepository = movementRepository;
        this.balanceRepository = balanceRepository;
        this.validator = validator;
    }

    @Transactional
    public InventoryMovementDto register(NewInventoryMovementDto dto) {
        // Validamos las reglas de negocio
        Item item = validator.validateItemExists(dto.getItemId());
        validator.validateMovement(dto);

        // Obtenemos el balance existente
        InventoryBalance existingBalance = balanceRepository.findByItemId(dto.getItemId())
                .orElseGet(() -> balanceRepository.save(
                        InventoryBalance.newInventoryBalance(item, BigDecimal.ZERO)
                ));

        // Validamos negativo
        if (dto.getType() == MovementType.OUT &&
                existingBalance.getCurrentQuantity().compareTo(dto.getQuantity()) < 0) {
            throw new HerkatException(ErrorMessage.INSUFFICIENT_STOCK);
        }

        // Creamos el movimiento en la base de datos
        InventoryMovement movement = NewInventoryMovementDto.toEntity(dto, item);

        // Guardamos el movimiento en la base de datos
        InventoryMovement saved = movementRepository.save(movement);

        // Actualizamos el balance
        BigDecimal newQuantity = applyMovement(
                existingBalance.getCurrentQuantity(),
                movement.getType(),
                movement.getQuantity()
        );

        // Actualizamos el DTO del balance
        UpdateInventoryBalanceDto updateBalanceDto = new UpdateInventoryBalanceDto(newQuantity);

        // Mapeamos los campos a la entidad
        InventoryBalance updatedBalance = UpdateInventoryBalanceDto.updateEntity(
                updateBalanceDto,
                existingBalance,
                null
        );

        balanceRepository.save(updatedBalance);

        return InventoryMovementDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public InventoryMovementDto findById(Integer id) {
        // Buscamos el movimiento por su ID
        return movementRepository.findById(id)
                .map(InventoryMovementDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MOVEMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<InventoryMovementDto> findByItemId(Integer itemId) {
        // Buscamos todos los movimientos por el ID de su ítem
        return movementRepository.findByItemId(itemId)
                .stream()
                .map(InventoryMovementDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryMovementDto> findByDateRange(LocalDateTime start, LocalDateTime end) {
        // Buscamos todos los movimientos por rango de fechas
        return movementRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(InventoryMovementDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryMovementDto> findByMovementType(MovementType movementType) {
        // Buscamos todos los movimientos por su tipo
        return movementRepository.findByType(movementType)
                .stream()
                .map(InventoryMovementDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryMovementDto> findByItemIdAndDateBetween(Integer itemId,
                                                                 LocalDateTime start, LocalDateTime end) {
        // Buscamos todos los movimientos por el ID de su ítem y un rango de fechas
        return movementRepository.findByItemIdAndCreatedAtBetween(itemId, start, end)
                .stream()
                .map(InventoryMovementDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public InventoryMovementDto findTopByItemIdOrderByDateDesc(Integer itemId) {
        // Buscamos el último movimiento por el ID del ítem
        return movementRepository.findTopByItemIdOrderByCreatedAtDesc(itemId)
                .map(InventoryMovementDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MOVEMENT_NOT_FOUND));
    }

    @Transactional
    public InventoryMovementDto update(Integer movementId, UpdateInventoryMovementDto dto) {
        // Obtenemos movimiento y balance
        InventoryMovement existingMovement = movementRepository.findById(movementId)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MOVEMENT_NOT_FOUND));

        InventoryBalance existingBalance = balanceRepository.findByItemId(existingMovement.getItem().getId())
                .orElseThrow(() -> new HerkatException(ErrorMessage.BALANCE_NOT_FOUND));

        // Revertimos el movimiento antiguo
        BigDecimal revertedQuantity;
        if (existingMovement.getType() == MovementType.OUT) {
            revertedQuantity = existingBalance.getCurrentQuantity().add(existingMovement.getQuantity());
        } else {
            revertedQuantity = existingBalance.getCurrentQuantity().subtract(existingMovement.getQuantity());
        }

        // Aplicamos el nuevo movimiento
        BigDecimal finalQuantity;
        if (dto.getType() == MovementType.OUT) {
            if (revertedQuantity.compareTo(dto.getQuantity()) < 0) {
                throw new HerkatException(ErrorMessage.INSUFFICIENT_STOCK);
            } else {
                finalQuantity = revertedQuantity.subtract(dto.getQuantity());
            }
        } else {
            finalQuantity = revertedQuantity.add(dto.getQuantity());
        }

        // Actualizamos el DTO del balance con la nueva cantidad final
        UpdateInventoryBalanceDto updateBalanceDto = new UpdateInventoryBalanceDto(finalQuantity);

        // Mapeamos los campos a la entidad
        InventoryBalance updatedBalance = UpdateInventoryBalanceDto.updateEntity(
                updateBalanceDto,
                existingBalance,
                null
        );

        // Guardamos los cambios del balance en la base de datos
        balanceRepository.save(updatedBalance);

        // Mapeamos el movimiento a la entidad
        InventoryMovement updatedMovement = UpdateInventoryMovementDto.updateEntity(
                dto,
                existingMovement,
                null
        );
        
        // Guardamos los cambios del movimiento en la base de datos
        InventoryMovement savedMovement = movementRepository.save(updatedMovement);

        // Convertimos la entidad a DTO para retornarlo
        return InventoryMovementDto.fromEntity(savedMovement);
    }

    @Transactional
    public void delete(Integer movementId) {
        // Obtenemos el movimiento y el balance
        InventoryMovement existingMovement = movementRepository.findById(movementId)
                .orElseThrow(() -> new HerkatException(ErrorMessage.MOVEMENT_NOT_FOUND));
        InventoryBalance existingBalance = balanceRepository.findByItemId(existingMovement.getItem().getId())
                .orElseThrow(() -> new HerkatException(ErrorMessage.BALANCE_NOT_FOUND));

        // Revertimos el movimiento
        BigDecimal revertedQuantity = revertMovement(
                existingBalance.getCurrentQuantity(),
                existingMovement.getType(),
                existingMovement.getQuantity()
        );

        // Validar negativo
        if (revertedQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new HerkatException(ErrorMessage.INSUFFICIENT_STOCK);
        }

        // Actualizamos el DTO del balance con el stock revertido
        UpdateInventoryBalanceDto updateBalanceDto = new UpdateInventoryBalanceDto(revertedQuantity);

        // Mapeamos los campos a la entidad
        InventoryBalance updatedBalance = UpdateInventoryBalanceDto.updateEntity(
                updateBalanceDto,
                existingBalance,
                null
        );

        // Guardamos los cambios del balance en la base de datos
        balanceRepository.save(updatedBalance);

        // Eliminamos el movimiento
        movementRepository.delete(existingMovement);
    }

    private BigDecimal applyMovement(BigDecimal oldQuantity, MovementType type, BigDecimal newQuantity) {
        return type == MovementType.OUT ? oldQuantity.subtract(newQuantity) : oldQuantity.add(newQuantity);
    }

    private BigDecimal revertMovement(BigDecimal oldQuantity, MovementType type, BigDecimal newQuantity) {
        return type == MovementType.OUT ? oldQuantity.add(newQuantity) : oldQuantity.subtract(newQuantity);
    }

}
