package com.herkat.repositories;

import com.herkat.models.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Integer> {

    Optional<ServiceItem> findByNameIgnoreCase(String name);

}
