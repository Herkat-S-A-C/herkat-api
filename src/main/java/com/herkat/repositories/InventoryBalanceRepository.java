package com.herkat.repositories;

import com.herkat.models.InventoryBalance;
import com.herkat.models.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryBalanceRepository extends JpaRepository<InventoryBalance, Integer> {

    Optional<InventoryBalance> findByItemId(Integer itemId);

    List<InventoryBalance> findByCurrentQuantityLessThan(BigDecimal quantity);

    List<InventoryBalance> findByItem_Type(ItemType type);

}
