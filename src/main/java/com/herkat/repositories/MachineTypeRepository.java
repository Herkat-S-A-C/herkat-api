package com.herkat.repositories;

import com.herkat.models.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, Integer> {

    Optional<MachineType> findByNameIgnoreCase(String name);

}
