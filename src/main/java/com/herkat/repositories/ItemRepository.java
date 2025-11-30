package com.herkat.repositories;

import com.herkat.models.Item;
import com.herkat.models.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByReferenceIdAndType(Integer referenceId, ItemType type);

}
