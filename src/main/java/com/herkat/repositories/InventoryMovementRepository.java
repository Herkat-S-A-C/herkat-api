package com.herkat.repositories;

import com.herkat.models.InventoryMovement;
import com.herkat.models.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Integer> {

    List<InventoryMovement> findByItemId(Integer itemId);

    List<InventoryMovement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<InventoryMovement> findByType(MovementType type);

    List<InventoryMovement> findByItemIdAndCreatedAtBetween(Integer itemId, LocalDateTime start, LocalDateTime end);

    Optional<InventoryMovement> findTopByItemIdOrderByCreatedAtDesc(Integer itemId);

}
