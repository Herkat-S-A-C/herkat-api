package com.herkat.repositories;

import com.herkat.models.ServiceItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceItemTypeRepository extends JpaRepository<ServiceItemType, Integer> {

    Optional<ServiceItemType> findByNameIgnoreCase(String name);

}
