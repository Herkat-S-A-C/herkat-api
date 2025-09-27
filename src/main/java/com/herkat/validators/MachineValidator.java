package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.repositories.MachineRepository;
import org.springframework.stereotype.Component;

@Component
public class MachineValidator {

    private final MachineRepository repository;

    public MachineValidator(MachineRepository repository) {
        this.repository = repository;
    }

    public void validateNameUniqueness(String name) {
        if(repository.findByNameIgnoreCase(name).isPresent()) {
            throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
        }
    }

    public void validateNameOnUpdate(Integer machineId, String name) {
        repository.findByNameIgnoreCase(name)
                .ifPresent(existingMachine -> {
                    if(!existingMachine.getId().equals(machineId)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
